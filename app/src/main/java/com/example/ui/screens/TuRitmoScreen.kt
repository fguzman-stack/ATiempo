package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.components.AmbientBackground
import com.example.ui.components.SweetAlertDialog
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TuRitmoScreen(navController: NavController, viewModel: MainViewModel) {
    val currentLang by viewModel.language.collectAsState()
    val totalCompletions by viewModel.totalCompletions.collectAsState()
    val currentStreak by viewModel.globalStreak.collectAsState()
    val maxStreak by viewModel.maxGlobalStreak.collectAsState()
    val bestHour by viewModel.bestHour.collectAsState()
    val completionsList by viewModel.completions.collectAsState()
    val remindersList by viewModel.reminders.collectAsState()

    val premiumUnlocked = totalCompletions >= 50
    var showStreakInfo by remember { mutableStateOf(false) }

    val latestActivities = remember(completionsList) {
        completionsList.sortedByDescending { it.completedAt }.take(5)
    }

    val historicalList = remember(completionsList) {
        completionsList.sortedByDescending { it.completedAt }
    }

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = Translations.getString("rhythm", currentLang),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Big counter card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = totalCompletions.toString(),
                                    fontSize = 72.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = Translations.getString("total_completions", currentLang),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }


                    // Streaks and Best Hour Row
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
// Streaks Card
                             Card(
                                 modifier = Modifier.weight(1f).fillMaxHeight(),
                                 shape = RoundedCornerShape(20.dp),
                                 colors = CardDefaults.cardColors(
                                     containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
                                 )
                             ) {
                                 Column(
                                     modifier = Modifier.padding(16.dp).fillMaxHeight(),
                                     verticalArrangement = Arrangement.SpaceBetween
                                 ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = Translations.getString("streak_label", currentLang),
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            IconButton(
                                                onClick = { showStreakInfo = true },
                                                modifier = Modifier.size(20.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Info,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                                    modifier = Modifier.size(13.dp)
                                                )
                                            }
                                        }
                                         Spacer(modifier = Modifier.height(2.dp))
                                         Text(
                                             text = "${currentStreak} ${Translations.getString("streak_days", currentLang)}",
                                             fontSize = 18.sp,
                                             fontWeight = FontWeight.ExtraBold,
                                             color = MaterialTheme.colorScheme.onSurface
                                         )
                                         if (currentStreak > 0) {
                                             Text(
                                                 text = Translations.getString("max_streak_short", currentLang).replace("%d", "$maxStreak"),
                                                 fontSize = 11.sp,
                                                 color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                             )
                                         } else if (totalCompletions > 0) {
                                             Text(
                                                 text = Translations.getString("streak_ended", currentLang),
                                                 fontSize = 11.sp,
                                                 color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                             )
                                         }
                                     }

                                    // Streak Animation - Cleanly placed AT THE BOTTOM
                                    val streakComposition by rememberLottieComposition(LottieCompositionSpec.Asset("tu_ritmo/Streak Fire.json"))
                                    val progress by animateLottieCompositionAsState(
                                        composition = streakComposition,
                                        iterations = LottieConstants.IterateForever,
                                        speed = 0.35f
                                    )
                                    
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        LottieAnimation(
                                            composition = streakComposition,
                                            progress = { progress },
                                            modifier = Modifier
                                                .size(60.dp)
                                                .alpha(0.9f),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            }

// Best Hour Card
                             Card(
                                 modifier = Modifier.weight(1f).fillMaxHeight(),
                                 shape = RoundedCornerShape(20.dp),
                                 colors = CardDefaults.cardColors(
                                     containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
                                 )
                             ) {
                                 Column(
                                     modifier = Modifier.padding(16.dp).fillMaxHeight(),
                                     verticalArrangement = Arrangement.SpaceBetween
                                 ) {
                                     Row(verticalAlignment = Alignment.CenterVertically) {
                                         Icon(
                                             imageVector = Icons.Default.Speed,
                                             contentDescription = "Best hour",
                                             tint = MaterialTheme.colorScheme.primary,
                                             modifier = Modifier.size(20.dp)
                                         )
                                         Spacer(modifier = Modifier.width(6.dp))
                                         Text(
                                             text = Translations.getString("best_time", currentLang),
                                             fontSize = 13.sp,
                                             fontWeight = FontWeight.Bold,
                                             color = MaterialTheme.colorScheme.onSurface
                                         )
                                     }
                                     
                                     Column {
                                         Text(
                                             text = if (bestHour != null) String.format("%02d:00", bestHour) else Translations.getString("few_data", currentLang),
                                             fontSize = 18.sp,
                                             fontWeight = FontWeight.ExtraBold,
                                             color = MaterialTheme.colorScheme.onSurface
                                         )
                                         Text(
                                             text = Translations.getString("optimal_focus", currentLang),
                                             fontSize = 11.sp,
                                             color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                         )
                                     }
                                     
                                     // Empty box for alignment symmetry
                                     Spacer(modifier = Modifier.height(60.dp))
                                 }
                             }
                         }
                    }

                    // Last 5 Activities Section
                    item {
                        Text(
                            text = Translations.getString("activities_title", currentLang),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }

                    if (latestActivities.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = Translations.getString("complete_first_reminder_desc", currentLang),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    } else {
                        items(latestActivities) { act ->
                            val isCompleted = act.status == "COMPLETED"
                            val timeStr = remember(act.completedAt) {
                                val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
                                sdf.format(Date(act.completedAt))
                            }

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (isCompleted) {
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                                    } else {
                                                        MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
                                                    }
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Alarm,
                                                contentDescription = act.status,
                                                tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
Column {
                                             val reminderName = remember(act.reminderId, remindersList) {
                                                 remindersList.find { it.id == act.reminderId }?.name ?: "#${act.reminderId}"
                                             }
                                             Text(
                                                 text = if (isCompleted) Translations.getString("completed", currentLang) else Translations.getString("postponed", currentLang),
                                                 fontSize = 14.sp,
                                                 fontWeight = FontWeight.Bold,
                                                 color = MaterialTheme.colorScheme.onSurface
                                             )
                                             Text(
                                                 text = reminderName,
                                                 fontSize = 10.sp,
                                                 color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                             )
                                         }
                                    }
                                    Text(
                                        text = timeStr,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                    if (!premiumUnlocked) {
                        item {
                            Text(Translations.getString("history_locked", currentLang), fontWeight = FontWeight.Bold)
                            Text(
                                Translations.getString("history_locked_desc", currentLang).format(totalCompletions),
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                    } else if (historicalList.isEmpty()) {
                        item {
                            Text(
                                text = Translations.getString("no_history_yet", currentLang),
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 80.dp)
                            )
                        }
                    } else {
                            items(historicalList) { item ->
                                val dateStr = remember(item.completedAt) {
                                    val sdf = SimpleDateFormat("dd/MM/yyyy • HH:mm:ss", Locale.getDefault())
                                    sdf.format(Date(item.completedAt))
                                }
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = remindersList.find { it.id == item.reminderId }?.name ?: "Recordatorio #${item.reminderId} - ${item.status}",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = dateStr,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f))
                                }
                            }
                            item { Spacer(modifier = Modifier.height(80.dp)) }
                        }
                }
            }
        }

        if (showStreakInfo) {
            SweetAlertDialog(
                icon = Icons.Default.Info,
                iconTint = MaterialTheme.colorScheme.onPrimary,
                iconBackground = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                title = Translations.getString("streak_label", currentLang),
                description = Translations.getString("streak_explanation", currentLang),
                confirmText = Translations.getString("ok", currentLang),
                onConfirm = { showStreakInfo = false },
                dismissText = "",
                onDismiss = { showStreakInfo = false }
            )
        }
    }
}
