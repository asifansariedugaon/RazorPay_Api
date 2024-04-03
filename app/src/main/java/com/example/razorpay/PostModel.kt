package com.example.razorpay

data class PostModel(
    val contact: String,
    val email: String,
    val name: String,
    val gstin: String,
    val fail_existing: String,
    val notes: NotesX
)