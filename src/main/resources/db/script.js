use
CartAPI

db.createCollection("users")
db.createCollection("cart")
db.createCollection("item")

/*Item*/

db.item.insertOne({
    "itemId": "8d1ec29c-03f4-11ec-9a03-0242ac130003",
    "name": "phone",
    "brand": "sumsung",
    "year": "2017",
    "model": "A80"
})

db.item.insertMany([{
    "itemId": "685e0fc2-e623-41f6-bee6-4939b3c6a1bc",
    "name": "tv",
    "brand": "sumsung",
    "year": "2020",
    "model": "A80"
},
    {
        "itemId": "27e8f400-03f5-11ec-9a03-0242ac130003",
        "name": "monitor",
        "brand": "LG",
        "year": "2018",
        "model": "K150"
    },

    {
        "itemId": "518ee1f2-03f5-11ec-9a03-0242ac130003",
        "name": "printer",
        "brand": "HP",
        "year": "2019",
        "model": "890H790"
    },

    {
        "itemId": "73920b26-03f5-11ec-9a03-0242ac130003",
        "name": "airpods",
        "brand": "apple",
        "year": "2020",
        "model": "cool"
    }

])

db.item.createIndex({itemId: 1}, {unique: true})

db.item.getIndexes()

db.item.find()

db.item.deleteOne({
    brand: "sumsung"
})


/* Cart*/

db.cart.createIndex({cartId: 1}, {unique: true})
db.cart.getIndexes()

db.cart.insertOne({
    "cartId": "cb21c184-03fe-11ec-9a03-0242ac130003",
    "item": [
        {
            "itemId": "685e0fc2-e623-41f6-bee6-4939b3c6a1bc"
        },
        {
            "itemId": "73920b26-03f5-11ec-9a03-0242ac130003"
        }
    ]

})


/* Users*/

db.users.createIndex({userId: 1}, {unique: true})
db.users.getIndexes()

db.users.insertOne({
    "userId" : "d32c3da0-0408-11ec-9a03-0242ac130003",
    "name": "Alex",
    "mail": "alexGr@gmail.com",
    "password": "$2a$12$ukH5EN3vizXrqWFTZ1enQuOLljSA5IgVFafiuOe/knVZ8qxoOg5uG",
    "role": "USER",
    "cartId": "cb21c184-03fe-11ec-9a03-0242ac130003"
})
//password: user

db.users.insertOne({
    "userId" : "ea3a5bec-04b9-11ec-9a03-0242ac130003",
    "name": "Mary",
    "mail": "maryCo@gmail.com",
    "password": "$2a$12$GKnqF.p2f2iiw1WXNVWVuO6VmjgYy3dtSReAA3KgrQKH7v44YzB4a",
    "role": "ADMIN",
})
//password: admin

db.users.find()

db.users.deleteOne({
    name: "Alex"
})



