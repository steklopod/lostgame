package tech.example.lostgame.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
data class UserSession(@Id val sessionId: UUID = UUID.randomUUID()) {
    @ManyToOne @JoinColumn(name = "user_id") @JsonIgnore
    var userBalance: UserBalance? = null
}
