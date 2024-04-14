package tech.example.lostgame.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import tech.example.lostgame.dto.Currency
import java.util.*

@Entity
data class BalanceTransaction(
    @Id
    val id: UUID,
    val amount: Long,
    @Enumerated(STRING)
    val currency: Currency,
    val gameSessionId: UUID,
) {
    @ManyToOne @JoinColumn(name = "user_id") @JsonIgnore
    var userBalance: UserBalance? = null
}
