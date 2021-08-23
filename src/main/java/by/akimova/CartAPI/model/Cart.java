package by.akimova.CartAPI.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;
import java.util.UUID;

/**
 * The Cart is a model of db's cart
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@Document(collection = "cart")
@Data
public class Cart {
    @Id
    private ObjectId _id;
    @Field(name = "id")
    @Indexed(unique = true)
    private UUID id;
    @Field(name = "numOfItems")
    private String numOfItems;
    @Field(name = "itemId")
    private Collection<Item> items;

}
