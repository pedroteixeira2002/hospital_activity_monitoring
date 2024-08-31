import json
import random
from faker import Faker

fake = Faker()

functions = ["Doctor", "Nurse", "Administrator", "Cleaner", "Security", "Visitor", "Patient"]

unique_ids = set()

people = []

while len(people) < 100:
    while True:
        person_id = random.randint(111111111, 999999999)
        if person_id not in unique_ids:
            unique_ids.add(person_id)
            break

    function = random.choice(functions)
    name = fake.name()
    location = random.randint(0, 50)
    age = random.randint(0, 120)

    person = {
        "function": function,
        "name": name,
        "location": location,
        "id": person_id,
        "age": age
    }

    people.append(person)

people_json = json.dumps(people, indent=2)

print(people_json)

with open('people.json', 'w') as json_file:
    json_file.write(people_json)
