package kh.edu.rupp.ite.memo.models

data class User(
    val _id: Long,
    val email: String,
    val password: String,
    val username: String,
    val updatedAt: String,
    val createdAt: String,
)
