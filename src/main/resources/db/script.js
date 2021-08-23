use
CartAPI

db.createCollection("users")
db.createCollection("cart")
db.createCollection("item")

/*Item*/

db.item.insertOne({
    "id": "8d1ec29c-03f4-11ec-9a03-0242ac130003",
    "name": "phone",
    "brand": "sumsung",
    "year": "2017",
    "model": "A80",
    "number": 50
})

db.item.insertMany([{
    "id": "685e0fc2-e623-41f6-bee6-4939b3c6a1bc",
    "name": "tv",
    "brand": "sumsung",
    "year": "2020",
    "model": "A80",
    "number": 50
},
    {
        "id": "27e8f400-03f5-11ec-9a03-0242ac130003",
        "name": "monitor",
        "brand": "LG",
        "year": "2018",
        "model": "K150",
        "number": 170
    },

    {
        "id": "518ee1f2-03f5-11ec-9a03-0242ac130003",
        "name": "printer",
        "brand": "HP",
        "year": "2019",
        "model": "890H790",
        "number": 20
    },

    {
        "id": "73920b26-03f5-11ec-9a03-0242ac130003",
        "name": "airpods",
        "brand": "apple",
        "year": "2020",
        "model": "cool",
        "number": 100
    }

])

db.item.createIndex({id: 1}, {unique: true})

db.item.getIndexes()

db.item.find()

db.item.deleteOne({
    brand: "sumsung"
})


/* Cart*/

db.cart.createIndex({id: 1}, {unique: true})
db.cart.getIndexes()

db.cart.insertOne({
    "id": "cb21c184-03fe-11ec-9a03-0242ac130003",
    "item": [
        {
            "id": "685e0fc2-e623-41f6-bee6-4939b3c6a1bc",
            "numItems": 1
        },
        {
            "id": "73920b26-03f5-11ec-9a03-0242ac130003",
            "numItems": 1
        }
    ]

})


/* Users*/

db.users.createIndex({id: 1}, {unique: true})
db.users.getIndexes()

db.users.insertOne({
    "id" : "d32c3da0-0408-11ec-9a03-0242ac130003",
    "name": "Alex",
    "mail": "alexGr@gmail.com",
    "password": "12345",
    "role": "user",
    "cart": "cb21c184-03fe-11ec-9a03-0242ac130003"
})
