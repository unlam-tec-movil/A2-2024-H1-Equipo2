package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.NavHostRouterPaths
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomButton
import ar.edu.unlam.mobile.scaffolding.ui.components.GradientComponent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        // Extiende el Column a toda la pantalla
    ) {
        Box {
            GradientComponent(400)

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Spacer(modifier = Modifier.padding(50.dp))
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_white),
                        contentDescription = "Logo",
                    )
                }

                Spacer(modifier = Modifier.padding(15.dp))
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomButton(
                    text = "Volver a jugar",
                    onClick = { controller.navigate(NavHostRouterPaths.GAME_CLASSIC.route) },
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = "Menu principal",
                    onClick = { controller.navigate(NavHostRouterPaths.GAME_ADVANCED.route) },
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
//    HomeScreen()
}
