package com.example.amazon_sim.ui.screen.address

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.address.components.AddressCard
import com.example.amazon_sim.ui.screen.address.components.AddressListTopBar
import com.example.amazon_sim.ui.screen.address.components.QuickEntryRow
import com.example.amazon_sim.ui.theme.AmazonDividerGray

@Composable
fun AddressScreen(
    viewModel: AddressViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val addresses by viewModel.addresses.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val editLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.loadAddresses()
    }

    Scaffold(
        topBar = {
            AddressListTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            item {
                Text(
                    text = "Your Addresses",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider(color = AmazonDividerGray)
            }

            item {
                QuickEntryRow(
                    text = "Add a new address",
                    onClick = {
                        val intent = Intent(context, AddEditAddressActivity::class.java)
                        editLauncher.launch(intent)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Personal Addresses",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(addresses, key = { it.id }) { address ->
                AddressCard(
                    address = address,
                    onEditClick = {
                        val intent = Intent(context, AddEditAddressActivity::class.java)
                        intent.putExtra("address_id", address.id)
                        editLauncher.launch(intent)
                    },
                    onRemoveClick = {
                        viewModel.deleteAddress(address.id)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
