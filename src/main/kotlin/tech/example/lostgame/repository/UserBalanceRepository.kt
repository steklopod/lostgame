package tech.example.lostgame.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.example.lostgame.model.UserBalance
import java.util.UUID

@Repository
interface UserBalanceRepository : JpaRepository<UserBalance, UUID>
