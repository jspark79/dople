package com.dople.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebasePost {
    public String email;
    public String name;
    public String location;
    public int age;
    public int gender;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String email, String name, String location, int age, int gender) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.age = age;
        this.gender = gender;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", name);
        result.put("location", location);
        result.put("age", age);
        result.put("gender", gender);
        return result;
    }
}