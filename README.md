Customers And Accounts Api. 

Requirements
Install "docker" in your enviroment
Free ports: 55432, 8080 and 8081
Note: if you want choose a port, please change in docker_compose-yml in ports secction for change database, customer and account ports. 

Description
I created two apis, one for customer and another for account, the communication between this apis is synchronous because transaction is online. Both apis are created with clean Architecture in layers


For deploying api
Step 
1.- Download all repository from main branch
2.- Go to directory: ..\Accounts\Docker
3.- Exec comand: docker-compose up --build

The aplication will deploy whit this features
1.- Construct a postgreSql database whit port 55432
2.- Construct a api Customers running in port 8081
3.- Construct a api Account running in port 8080

For testing aplication
1.- get a "postman" or another client
2.- import  Challengue.postman_collection file from dir ..\Accounts\Docker to postman
3.- First use endpoint creating a customer, after create a account for customer whit the same identity. 
4.- Create a movement for the account created after above stepts
