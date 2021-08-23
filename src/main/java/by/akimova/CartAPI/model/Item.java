package by.akimova.CartAPI.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * The Item is a model of db's item
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@Document(collection = "item")
@Data
public class Item {
    @Id
    private ObjectId _id;
    @Field(name = "id")
    @Indexed(unique = true)
    private UUID id;
    @Field(name = "name")
    private String name;
    @Field(name = "brand")
    private String brand;
    @Field(name = "year")
    private String year;
    @Field(name = "number")
    private Integer number;
}
