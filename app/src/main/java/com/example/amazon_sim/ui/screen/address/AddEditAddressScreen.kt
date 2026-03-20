package com.example.amazon_sim.ui.screen.address

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.amazon_sim.ui.screen.address.components.AddEditTopBar
import com.example.amazon_sim.ui.screen.address.components.AddressDualField
import com.example.amazon_sim.ui.screen.address.components.AddressFormField
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonLinkBlue
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder

@Composable
fun AddEditAddressScreen(
    viewModel: AddressViewModel,
    onBackClick: () -> Unit,
    onSaved: () -> Unit
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val title = if (formState.isEditMode) "Edit address" else "Add an address"

    Scaffold(
        topBar = {
            AddEditTopBar(title = title, onBackClick = onBackClick)
        },
        bottomBar = {
            Button(
                onClick = {
                    if (viewModel.validateAndSave()) {
                        onSaved()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmazonCheckoutYellow,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Use this address",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            if (!formState.isEditMode) {
                Text(
                    text = "Enter a new shipping address",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            AddressFormField(
                label = "Country/Region",
                value = formState.country,
                onValueChange = { viewModel.updateFormField("country", it) },
                showClearButton = true
            )

            AddressFormField(
                label = "Full name (first and last name)",
                value = formState.fullName,
                onValueChange = { viewModel.updateFormField("fullName", it) },
                showClearButton = true,
                isError = formState.errors.containsKey("fullName"),
                errorText = formState.errors["fullName"] ?: ""
            )

            AddressFormField(
                label = "Phone number",
                value = formState.phoneNumber,
                onValueChange = { viewModel.updateFormField("phoneNumber", it) },
                helperText = "May be used to assist delivery",
                keyboardType = KeyboardType.Phone
            )

            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(4.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmazonSearchBarBorder)
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = AmazonLinkBlue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Use my location", color = AmazonLinkBlue, fontSize = 14.sp)
            }

            AddressDualField(
                label = "Address",
                topValue = formState.streetAddress,
                topPlaceholder = "Street address or P.O. Box",
                onTopValueChange = { viewModel.updateFormField("streetAddress", it) },
                bottomValue = formState.aptSuite,
                bottomPlaceholder = "Apt, suite, unit, building, floor, etc.",
                onBottomValueChange = { viewModel.updateFormField("aptSuite", it) },
                isError = formState.errors.containsKey("streetAddress"),
                errorText = formState.errors["streetAddress"] ?: ""
            )

            AddressFormField(
                label = "City",
                value = formState.city,
                onValueChange = { viewModel.updateFormField("city", it) },
                isError = formState.errors.containsKey("city"),
                errorText = formState.errors["city"] ?: ""
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.weight(0.55f)) {
                    AddressFormField(
                        label = "State",
                        value = formState.state,
                        onValueChange = { viewModel.updateFormField("state", it) },
                        placeholder = ""
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(0.45f)) {
                    AddressFormField(
                        label = "ZIP Code",
                        value = formState.zipCode,
                        onValueChange = { viewModel.updateFormField("zipCode", it) },
                        keyboardType = KeyboardType.Number,
                        isError = formState.errors.containsKey("zipCode"),
                        errorText = formState.errors["zipCode"] ?: ""
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.isDefault,
                    onCheckedChange = { viewModel.toggleDefault() }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Make this my default address",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
