package by.akimova.CartAPI.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * The User is a model of db's user
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId _id;
    @Field(name = "userId")
    @Indexed(unique = true)
    private UUID userId;
    @Field(name = "name")
    private String name;
    @Field(name = "mail")
    private String mail;
    @Field(name = "password")
    private String password;
    @Field(name = "role")
    private Role role;
}

