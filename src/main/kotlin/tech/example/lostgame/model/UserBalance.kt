package tech.example.lostgame.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.*

@Entity
data class UserBalance(
    @Id
    val userId: UUID,
    val userNick: String,
    var amount: Long,
    val denomination: Int,
    val maxWin: Long,
    val currency: String,
    val jpKey: String? = null
){
    @OneToMany(mappedBy = "userBalance", cascade = [ALL]) @JsonIgnore
    var sessions: List<UserSession> =  listOf()

    @OneToMany(mappedBy = "userBalance", cascade = [ALL]) @JsonIgnore
    var balanceTransactions: List<BalanceTransaction> =  listOf()
}
