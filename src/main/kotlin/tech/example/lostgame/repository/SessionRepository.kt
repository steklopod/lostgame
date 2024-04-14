package tech.example.lostgame.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.example.lostgame.model.UserSession
import java.util.*

@Repository
interface SessionRepository : JpaRepository<UserSession, UUID>
