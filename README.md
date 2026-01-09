# Sports Venue Booking System
A dockerized Spring Boot backend for managing sports venues, time slots, and bookings with conflict prevention and availability checks. Simulates a real-world sports ground/turf booking system.

## TechStack
- Backend: Java, Spring Boot
- Database: MySQL
- Containerization: Docker, Docker Compose
- API: REST

## Project Structure
1. **Orchestrator Module**:Main Spring Boot App managing feature modules. Feature modules are libraries, not deployable services
2. **Feature Modules**: Libraries containing controllers, service and DAOs
3. **Projections module**: Shared DTOs, models, exception handlers etc
4. **Docker**: App and MySQL containerized for one-command startup

## Implementation Key Points
1. **Slot-management**: Fetching available slots within a time range implies:
   - Any slot that overlaps with the time range entered by user
   - Covers Partial overlap
   - Existing db slots start time < user's end time 
   - Existing db slots end time > user's start time

2. **Booking management**: Booking a time slot for a venue and respective sport
   - Design considers slot as the source of truth.
   - Booking references slotId.
   - Uses row level locking to prevent more than one active bookings for the same slot

## Curl Requests

1. **Fetch sports from stapubox api and store in DB**
   `curl --location --request POST 'http://localhost:8080/sports/sync' \
      --data ''`

2. **API to add a venue**
    `curl --location 'http://localhost:8080/venues' \
   --header 'Content-Type: application/json' \
   --data '{
   "name": "Elite Sports Academy",
   "location" : "Noida, UP",
   "sportCodes": ["7020104", "7020111"]
   }'`

3. **API to fetch a venue**
    `curl --location 'http://localhost:8080/venues/2'`

4. **API to delete a venue**
   `curl --location --request DELETE 'http://localhost:8080/venues/1'`

5. **API to add a slot**
    `curl --location 'http://localhost:8080/venues/2/slots' \
   --header 'Content-Type: application/json' \
   --data '{
   "venueId": 2,
   "sportId": 6,
   "startTime": "2026-01-10T04:00:00",
   "endTime": "2026-01-10T05:30:00"
   }'`

6. **API to find available slots based on time range and sport**
   `curl --location 'http://localhost:8080/venues/available?sportId=6&start=2026-01-10T03%3A00%3A00&end=2026-01-10T05%3A30%3A00'`

7. **API to add a booking**
   `curl --location 'http://localhost:8080/bookings' \
   --header 'Content-Type: application/json' \
   --data '{
   "slotId": 3
   }'`

8. **API to cancel a booking**
   `curl --location --request PUT 'http://localhost:8080/bookings/2/cancel' \
   --data ''`