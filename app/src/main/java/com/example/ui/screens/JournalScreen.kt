package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.data.database.JournalEntity
import com.example.ui.components.AmbientBackground
import com.example.ui.components.SweetAlertDialog
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun JournalScreen(navController: NavController, viewModel: MainViewModel) {
    val currentLang by viewModel.language.collectAsState()
    val journals by viewModel.journals.collectAsState()

    var textFieldValue by remember { mutableStateOf("") }
    var showDeleteConfirm by remember { mutableStateOf<JournalEntity?>(null) }
    var editingEntry by remember { mutableStateOf<JournalEntity?>(null) }

    val cal = remember { Calendar.getInstance() }
    val todayLabel = remember {
        String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
    }

    val todayEntries = remember(journals, todayLabel) {
        journals.filter { it.dateLabel == todayLabel }
    }

    val pastEntries = remember(journals, todayLabel) {
        journals.filter { it.dateLabel != todayLabel }
    }

    val groupedPast = remember(pastEntries) {
        pastEntries.groupBy { it.dateLabel }.toSortedMap(compareByDescending { it })
    }

    val totalCount = journals.size

    // Edit state
    var editText by remember { mutableStateOf("") }
    val isEditing = editingEntry != null

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            viewModel.triggerHapticFeedback()
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = Translations.getString("journal_title", currentLang),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = Translations.getString("journal_subtitle", currentLang),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                        }
                    }
                }

                // Stats chip
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "$totalCount ${Translations.getString("journal_entries_count", currentLang)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        if (todayEntries.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            Text(
                                text = "${todayEntries.size} ${Translations.getString("journal_today_count", currentLang)}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // Today's entries section
                if (todayEntries.isNotEmpty()) {
                    item {
                        Text(
                            text = Translations.getString("journal_today_section", currentLang),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    items(todayEntries, key = { "today_${it.id}" }) { entry ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically { -20 }
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        val timeStr = remember(entry.createdAt) {
                                            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                                            sdf.format(Date(entry.createdAt))
                                        }
                                        Text(
                                            text = entry.content,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = timeStr,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.triggerHapticFeedback()
                                            editingEntry = entry
                                            editText = entry.content
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Create,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.triggerHapticFeedback()
                                            showDeleteConfirm = entry
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // New entry card / Edit card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = if (isEditing) Translations.getString("journal_edit_entry", currentLang)
                                       else Translations.getString("journal_today", currentLang),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = if (isEditing) editText else textFieldValue,
                                onValueChange = { newVal ->
                                    if (newVal.length <= 500) {
                                        if (isEditing) editText = newVal
                                        else textFieldValue = newVal
                                    }
                                },
                                placeholder = {
                                    Text(
                                        text = Translations.getString("journal_placeholder", currentLang),
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                shape = RoundedCornerShape(14.dp),
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp
                                ),
                                supportingText = {
                                    val currentLength = if (isEditing) editText.length else textFieldValue.length
                                    val isNearLimit = currentLength >= 450
                                    Text(
                                        text = "$currentLength/500",
                                        color = if (isNearLimit) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.End
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (isEditing) {
                                        TextButton(onClick = {
                                            editingEntry = null
                                            editText = ""
                                        }) {
                                            Text(
                                                text = Translations.getString("cancel", currentLang),
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                            )
                                        }
                                    }
                                    TextButton(
                                        onClick = {
                                            val text = if (isEditing) editText.trim() else textFieldValue.trim()
                                            if (text.isNotEmpty()) {
                                                viewModel.triggerHapticFeedback()
                                                if (isEditing) {
                                                    val updated = editingEntry!!.copy(content = text)
                                                    viewModel.updateJournalEntry(updated)
                                                    editingEntry = null
                                                    editText = ""
                                                } else {
                                                    viewModel.addJournalEntry(text)
                                                    textFieldValue = ""
                                                }
                                            }
                                        },
                                        enabled = if (isEditing) editText.isNotBlank() else textFieldValue.isNotBlank()
                                    ) {
                                        Text(
                                            text = if (isEditing) Translations.getString("journal_save_edit", currentLang)
                                                   else Translations.getString("journal_save", currentLang),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if ((if (isEditing) editText else textFieldValue).isBlank())
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                            else MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Past entries
                if (groupedPast.isEmpty() && todayEntries.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val emptyComposition by rememberLottieComposition(LottieCompositionSpec.Asset("diario/libro_diario.json"))
                            val emptyProgress by animateLottieCompositionAsState(
                                composition = emptyComposition,
                                iterations = LottieConstants.IterateForever,
                                speed = 0.7f
                            )
                            LottieAnimation(
                                composition = emptyComposition,
                                progress = { emptyProgress },
                                modifier = Modifier.size(140.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = Translations.getString("journal_empty", currentLang),
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                } else if (groupedPast.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = Translations.getString("journal_past_section", currentLang),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    groupedPast.forEach { (dateLabel, entries) ->
                        item {
                            val displayDate = remember(dateLabel) {
                                try {
                                    val parts = dateLabel.split("-")
                                    val c = Calendar.getInstance()
                                    c.set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())
                                    val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Translations.getLocale(currentLang))
                                    sdf.format(c.time)
                                } catch (e: Exception) { dateLabel }
                            }
                            Text(
                                text = displayDate,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                            )
                        }

                        items(entries, key = { "past_${it.id}" }) { entry ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.25f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = entry.content,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        val timeStr = remember(entry.createdAt) {
                                            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                                            sdf.format(Date(entry.createdAt))
                                        }
                                        Text(
                                            text = timeStr,
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.triggerHapticFeedback()
                                            editingEntry = entry
                                            editText = entry.content
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Create,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.triggerHapticFeedback()
                                            showDeleteConfirm = entry
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    // Delete confirmation
    showDeleteConfirm?.let { entry ->
        SweetAlertDialog(
            icon = Icons.Default.Delete,
            iconTint = Color(0xFFE53935),
            iconBackground = Color(0xFFFFEBEE),
            title = Translations.getString("journal_delete_title", currentLang),
            description = "\"${entry.content}\"",
            confirmText = Translations.getString("delete", currentLang),
            onConfirm = {
                viewModel.triggerHapticFeedback()
                viewModel.deleteJournalEntry(entry)
                showDeleteConfirm = null
            },
            dismissText = Translations.getString("cancel", currentLang),
            onDismiss = { showDeleteConfirm = null }
        )
    }
}
