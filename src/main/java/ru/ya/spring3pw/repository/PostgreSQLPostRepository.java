package ru.ya.spring3pw.repository;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Conditional;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.condition.PostgreSQLDataBaseCondition;

import java.util.Arrays;
import java.util.List;

@Repository
@Conditional(PostgreSQLDataBaseCondition.class)
public class PostgreSQLPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    private final String BASE_POST_SEARCH_QUERY = """
                SELECT id,
                       title,
                       text,
                       tags,
                       likesCount,
                       (SELECT COUNT(1) FROM comments c
                       WHERE c.post_id = p.id) AS commentsCount
                FROM posts p
            """;

    private final String BASE_COMMENT_SEARCH_QUERY = """
                SELECT id,
                       post_id,
                       text
                FROM comments
            """;

    public PostgreSQLPostRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    @Override
    public List<Post> getPosts(String search) {
        StringBuilder sqlBuilder = new StringBuilder(BASE_POST_SEARCH_QUERY);

        if (!search.isEmpty()) {
            boolean isFirstCriteria = true;
            List<String> criterias = Arrays.stream(search.trim().replaceAll("\\s+", " ").split(" ")).toList();
            StringBuilder searchByTitleBuilder = new StringBuilder();

            for (var criteria : criterias) {
                if (criteria.startsWith("#")) {
                    if (isFirstCriteria) {
                        sqlBuilder.append(" WHERE ").append("tags LIKE '%").append(criteria.substring(1)).append("%'");
                        isFirstCriteria = false;
                    } else
                        sqlBuilder.append(" AND ").append("tags LIKE '%").append(criteria.substring(1)).append("%'");
                } else
                    searchByTitleBuilder.append(criteria).append(" ");
            }

            if (!searchByTitleBuilder.isEmpty()) {
                searchByTitleBuilder.setLength(searchByTitleBuilder.length() - 1);

                if (isFirstCriteria)
                    sqlBuilder.append(" WHERE ").append("title LIKE '%").append(searchByTitleBuilder).append("%'");
                else
                    sqlBuilder.append(" AND ").append("title LIKE '%").append(searchByTitleBuilder).append("%'");
            }
        }

        return jdbcTemplate.query(
                sqlBuilder.toString(),
                (rs, rowNum) -> new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        convertToShortStr(rs.getString("text"), environment.getProperty("post.shorttext.size", Integer.class)),
                        convertStrtoList(rs.getString("tags")),
                        rs.getInt("likesCount"),
                        rs.getInt("commentsCount")
                ));
    }

    private String convertToShortStr(String text, Integer limit) {
        StringBuilder shortText = new StringBuilder(text);
        if (shortText.length() < limit) return shortText.toString();
        else {
            shortText.setLength(limit);
            return shortText.append("...").toString();
        }
    }

    private List<String> convertStrtoList(String tagsStr) {
        if (tagsStr.equals("[]")) return List.of();
        else return Arrays.stream(tagsStr.substring(1, tagsStr.length() - 1).split(", ")).toList();
    }

    @Override
    public Post getPost(Integer id) {
        StringBuilder sqlBuilder = new StringBuilder(BASE_POST_SEARCH_QUERY);
        sqlBuilder.append(" WHERE ").append("id = ").append(id);

        return jdbcTemplate.query(
                sqlBuilder.toString(),
                (rs, rowNum) -> new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        convertStrtoList(rs.getString("tags")),
                        rs.getInt("likesCount"),
                        rs.getInt("commentsCount")
                )).getFirst();
    }
}