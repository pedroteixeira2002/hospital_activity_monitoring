import random
import json
from datetime import datetime, timedelta

people_filename = "people.json"

with open(people_filename, 'r') as file:
    people_data = json.load(file)

person_ids = [person["id"] for person in people_data]


def random_datetime(start, end):

    return start + timedelta(seconds=random.randint(0, int((end - start).total_seconds())))


start_datetime = datetime(2024, 1, 1)
end_datetime = datetime(2024, 12, 31)

# Generate 700 events
events = []
for _ in range(700):
    event = {
        "fromRoomId": random.randint(0, 49),  # Random room ID from 0 to 49
        "toRoomId": random.randint(0, 49),  # Random room ID from 0 to 49
        "personId": random.choice(person_ids),  # Random person ID from the list
        "time": random_datetime(start_datetime, end_datetime).isoformat()
    }

    # Ensure fromRoomId and toRoomId are not the same
    while event["fromRoomId"] == event["toRoomId"]:
        event["toRoomId"] = random.randint(0, 49)

    events.append(event)

# Write events to a JSON file
events_filename = "events.json"
with open(events_filename, 'w') as f:
    json.dump(events, f, indent=2)

print(f"{len(events)} events generated and saved to {events_filename}.")
