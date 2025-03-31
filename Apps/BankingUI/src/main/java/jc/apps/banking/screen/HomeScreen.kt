package jc.apps.banking.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jc.apps.banking.widget.BottomNavigationBar
import jc.apps.banking.widget.CardsSection
import jc.apps.banking.widget.CurrenciesSection
import jc.apps.banking.widget.FinanceSection
import jc.apps.banking.widget.WalletSection

@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WalletSection()

            CardsSection()

            Spacer(modifier = Modifier.height(16.dp))

            FinanceSection()

            CurrenciesSection()
        }
    }
}