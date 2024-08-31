import json
import random

with open('rooms.json', 'r') as json_file:
    rooms = json.load(json_file)

num_rooms = len(rooms)

edge_density = 0.3

edges = []

for i in range(num_rooms):
    for j in range(i + 1, num_rooms):
        if random.random() < edge_density:
            edges.append({
                "room1": i,
                "weight": random.randint(1, 20),  # Random weight between 1 and 20
                "room2": j
            })

with open('hospital_map.json', 'w') as json_file:
    json.dump(edges, json_file, indent=4)

print("JSON file 'hospital_map.json' has been created with the hospital map.")
