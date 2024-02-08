package com.lunardev.kotlinallfeature.feature.fingerprint.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lunardev.kotlinallfeature.R
import com.lunardev.kotlinallfeature.core.component.appBar
import com.lunardev.kotlinallfeature.core.component.authenticateWithBiometric
import com.lunardev.kotlinallfeature.feature.gps.ui.FingerprintViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FingerPrintScreen(
    navigateBack: () -> Unit,
    isExpandedScreen: Boolean,
){
    Scaffold(
        topBar = {
            appBar(navigateBack = navigateBack, title = stringResource(id = R.string.menu_content_fingerprint))
        }, content = { innerPadding ->
            val context = LocalContext.current

            val fingerprintViewModel: FingerprintViewModel = viewModel(
                factory = FingerprintViewModel.provideFactory(context = context)
            )

            LaunchedEffect(Unit) {

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Image(
                    painter = painterResource(id = R.drawable.fingerprint_2313448),
                    contentDescription = stringResource(id = R.string.no_image),
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 20.dp)
                )

                if (fingerprintViewModel.canAuthenticateWithBiometrics) {
                    //TODO perform biometric authentication
                } else {
                    Text(
                        text = "Biometric authentication is not available on this device.",
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        authenticateWithBiometric(context){
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                    Text(stringResource(id = R.string.scan_biometric))
                }
            }

        })
}

@Preview
@Composable
fun FingerPrintScreenReview() {
    FingerPrintScreen(
        navigateBack = {

        },
        isExpandedScreen = false,
    )
}