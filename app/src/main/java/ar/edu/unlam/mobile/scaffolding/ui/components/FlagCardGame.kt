package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun FlagCardGame(flag: Int, pts: Int = 0, actualCard: Int = 0, modifier: Modifier) {
    Column(modifier.offset(y = (-50).dp)) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .offset(y = 50.dp)
                .zIndex(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            //Reemplazar Box por futuro componente temporizador
            Box(
                modifier = Modifier
                    .height(85.dp)
                    .width(85.dp)
                    .padding(12.dp)
                    .border(4.dp, Color(0xFFC4007A), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "3",
                    color = Color(0xFFC4007A),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .padding(horizontal = 36.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {

            Row(
                modifier = modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${actualCard.toString()}/10",
                    color = Color(0xFFC4007A),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .wrapContentHeight()
            ) {
                Image(
                    painter = painterResource(id = flag),
                    contentDescription = "Bandera",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = modifier.height(16.dp))

                // Points text
                Text(
                    text = "${pts.toString()}pts.",
                    color = Color(0xFFC4007A),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }

}