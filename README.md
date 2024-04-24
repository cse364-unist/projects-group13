# CSE364 Group Project - G.13

## Before starting the project...

Checkout _Git Template_ first. If you downloaded this repository on your local, you can simply execute the following command on the root.

```
git config --global commit.template .gitmessage.txt
```

> **IMPORTANT!!!!**
> 
> Divide branch for each work!!!
> 
> - feature1: @sean113x
> - feature-3: @queso-gato1355

## How to excute?

1. Download the zip file from this repository.
2. In there, type this:
   ```bash
   docker build -t (your custom name) . 
   ```
   > DON'T FORGET THE DOT AT THE END!!
3. Wait until the build ends.
4. After the build ended, type this:
   ```bash
   docker run -it (your custom name)
   ```
5. If it runs succesfully, then this command line should appear.
   ```
   root@(containerId):~/project#
   ```
6. Enter `sh run.sh` or `bash run.sh` and wait until you see this:
   ```
   Movies Database has been loaded.
   ```
   > This takes at least 50 seconds. Wait until all the database loaded. If you call any API before this done, nothing would work.
7. Open another terminal and type this:
   ```
   docker exec -it (your custom name) /bin/bash
   ```
8. And now you can use `curl`.

## API Explain
### Movie

##### `curl -X GET http://localhost:8080/movies{?year, genre}`
| parameters | type | description |
|---|---|---|
| `year` | Integer | The year that movies screened. |
| `genre` | List<String> | The genre that movies have |

You can search all the movies and query parameters.
For `genre`, you can request like this to get all the movies that has `Animation` and `Action` genres.
```curl
curl -X GET http://localhost:8080/movies?genre=Animation,Action
```

##### `curl -X GET http://localhost:8080/movies/{id}`
You can access the specific movie by ID.

##### `curl -X POST http://localhost:8080/movies -H ‘Content-type:application/json’ -d '{"id": "(movie id)", "title": "(title)", "year": (year), "genres":["Genre1", "Genre2", ...]}'`
You can request the POST opperation with this body. `year` field should be an integer. If the ID is already exists, PUT opperation is automatically executed.

##### `curl -X PUT http://localhost:8080/movies/{id} -H ‘Content-type:application/json’ -d '{"title": "(title)", "year": (year), "genres":["Genre1", "Genre2", ...]}'`
You can request PUT opperation with this body. `year` field should be an integer. If the ID does not exist, error would occur.

##### `curl -X DELETE http://localhost:8080/movies/{id}`
> **WARNING!**
> This opperation hadn't been tested.

You can request DELETE opperation by giving the url with ID. 

### User

##### `curl -X GET http://localhost:8080/users{?gender, age, occupation, postal}`
| parameters | type | description |
|---|---|---|
| `gender` | Character | The gender of user. `F` or `M` |
| `age` | Integer | The age of the user. Check out the appendix for more information. |
| `occupation` | Integer | The occupation of the user. Check out the appendix for more information. |
| `postal` | String | The postal of the user. |

You can request GET with and without query parameters. If you don't write query parameters, all user information would be given.

##### `curl -X GET http://localhost:8080/users/{id}`
You can access the specific user with the ID.

##### `curl -X POST http://localhost:8080/users -H ‘Content-type:application/json’ -d '{"id": "(user id)", "gender": "(F or M)", "age": (age), "occupation" : (occupation), "postal": "(postal)"}'`

You can request POST opperation with this body. If the ID is already exists, PUT opperation is automatically executed.

##### `curl -X PUT http://localhost:8080/users/{id} -H ‘Content-type:application/json’ -d '{"gender": "(F or M)", "age": (age), "occupation" : (occupation), "postal": "(postal)"}'`

You can request PUT opperation with this body. If the ID does not exist, error would occur. 

##### `curl -X DELETE http://localhost:8080/users/{id}`
> **WARNING!**
> This opperation hadn't been tested.

You can request DELETE opperation by giving the url with ID. 

### Rating

Rating API does not have an opperation calling all the rating datas due to the responsing time problem.

##### `curl -X GET http://localhost:8080/ratings/{rating}{?year, genre}`
| parameters | type | description |
|---|---|---|
| `year` | Integer | The year that movies screened. |
| `genre` | List<String> | The genre that movies have |

You can search all the movies that its average rating is greater then, or equeal to given `rating`. **`rating` should be an Integer between 1 and 5.**
For `genre`, you can request like this to get all the movies that its average rating is greater then, or equal to given `rating` and has `Animation` and `Action` genres.
```curl
curl -X GET http://localhost:8080/ratings/4?genre=Animation,Action
```

##### `curl -X GET http://localhost:8080/ratings/id/{id}`
You can request the specific rating entry with ID.

##### `curl -X GET http://localhost:8080/ratings/movie/{movieId}`
You can request all the rating entries with movie ID.

##### `curl -X GET http://localhost:8080/ratings/user/{userId}`
You can request all the rating entries with user ID.

##### `curl -X POST http://localhost:8080/ratings -H ‘Content-type:application/json’ -d '{"movieId": "(movie id)", "userId": "(user id)", "rate":(rate), "timestamp":"(timestamp)"}'`
You can request POST opperation with this body. `rate` should be an Integer. If the ID is already exists, PUT opperation is automatically executed.

##### `curl -X PUT http://localhost:8080/ratings/id/{id} -H ‘Content-type:application/json’ -d '{"movieId": "(movie id)", "userId": "(user id)", "rate":(rate), "timestamp":"(timestamp)"}'`
You can request PUT opperation with this body. `rate` should be an Integer. If the ID does not exist, error would occur. ID is the MongoDB's basic ID. It is not a movie ID nor user ID.

##### `curl -X DELETE http://localhost:8080/ratings/id/{id}`
> **WARNING!**
> This opperation hadn't been tested.

You can request DELETE opperation by giving the url with ID. 

## Appendix

### User API informations.
- Age is chosen from the following ranges:

| value | meaning |
| --- | --- |
| 1 | Under 18 |
| 18 | 18 - 24 |
| 25 | 25 - 34 |
| 35 | 35 - 44 |
| 45 | 45 - 54 |
| 56 | 56 + |

- Occupation is chosen from the following choices:

| value | meaning |
| --- | --- |
| 0 |  other or not specified |
| 1 | academic/educator |
| 2 | artist |
| 3 | clerical/admin |
| 4 | college/grad student |
| 5 | customer service |
| 6 | doctor/health care |
| 7 | executive/managerial |
| 8 | farmer |
| 9 | homemaker |
| 10 | K-12 student |
| 11 | lawyer |
| 12 | programmer |
| 13 | retired |
| 14 | sales/marketing |
| 15 | scientist |
| 16 | self-employed |
| 17 | technician/engineer |
| 18 | tradesman/craftsman |
| 19 | unemployed |
| 20 | writer |