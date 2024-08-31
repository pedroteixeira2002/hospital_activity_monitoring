import json
import random

# Define the possible types and access enumerations
room_types = [
    "Consultation",
    "Surgery",
    "Waiting",
    "Recovery",
    "Hospitalization",
    "Emergency",
    "Storage",
    "Restroom",
    "Kitchen",
    "Office",
    "Canteen",
    "Cafe",
    "Common",
    "Bathroom",
    "Laboratory",
    "Pharmacy",
    "Imaging",
    "Reception",
    "Laundry",
    "Meeting",
    "Library",
    "Church",
    "Exit"
]

access_types = ["Doctor", "Nurse", "Administrator", "Cleaner", "Security", "Visitor", "Patient"]


def generate_room_name(room_type):
    return f"{room_type} {random.randint(1, 100)}"


rooms = []
room_id = 1

# Generate 7 EXIT type rooms
for _ in range(7):
    capacity = random.randint(1, 30)
    current_occupation = random.randint(0, capacity)
    occupied = (current_occupation == capacity)
    room = {
        "id": room_id,
        "name": generate_room_name("Exit"),
        "type": "EXIT",
        "capacity": capacity,
        "currentOccupation": current_occupation,
        "occupied": occupied,
        "access": random.sample(access_types, random.randint(1, len(access_types)))
    }
    rooms.append(room)
    room_id += 1

for _ in range(43):
    room_type = random.choice(room_types[:-1])  # Exclude "Exit" from selection
    capacity = random.randint(1, 30)
    current_occupation = random.randint(0, capacity)
    occupied = (current_occupation == capacity)
    room = {
        "id": room_id,
        "name": generate_room_name(room_type),
        "type": room_type,
        "capacity": capacity,
        "currentOccupation": current_occupation,
        "occupied": occupied,
        "access": random.sample(access_types, random.randint(1, len(access_types)))
    }
    rooms.append(room)
    room_id += 1

with open('rooms.json', 'w') as json_file:
    json.dump(rooms, json_file, indent=4)

print("JSON file 'rooms.json' has been created with 50 rooms.")
