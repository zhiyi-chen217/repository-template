# Project requirements


## Main function
    
Room reservation system



## Sub functions (in this priority)
    
Bike reservation system
Food ordering system 

## Users

- Students:

    - Username and password are enough for authentication 

- Employees:

    - Username and password are enough for authentication
- Administrator:
    - Adds any data (add or delete buildings for example)
    - We should build another user interface special for the admin
    - Admin can give employee status to a user
    - Makes a menu for each restaurant

## User interface
- Easy to use (convenient)
- Pretty

## Relevant entities

- Building:

    - Opening time

    - Closing time

    - Location

    - Pictures

    - Name

- Rooms:

    - Picture

    - Description

    - Capacity

    - Name

    - Can be reserved by student or only employee
    - TV
    - Whiteboard
    - Availability
- Food:
  - 2 ways to order:
    - pick up
    - reserve (have your food delivered to your room if it is in the same building)
- Bikes:
    - Can be picked up from different buildings
    - Different timeslots to reserve each bike
    - Can be returned to different buildings
      (can be returned to a building where it was not picked up)
    - Number of bikes at every building should be shown
    - Cannot reserve bikes if there are no bikes available

## Reservations
- Overview of all reservations per user
- Calendar view (like Google Calendar)
- User can only see his reservations
- User can search for a room with specific attribute (a whiteboard for example)
- 2 weeks upfront reservation in advance

## Other details
- In different buildings rooms can have the same name
- Each building CAN have a restaurant (it can have 0, 1 or more restaurants)
- Not allowed to use websockets


