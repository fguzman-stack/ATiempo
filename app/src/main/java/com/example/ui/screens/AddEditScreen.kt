package com.example.ui.screens

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.database.ReminderEntity
import com.example.ui.components.AmbientBackground
import com.example.ui.translation.Translations
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.core.content.ContextCompat
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

// Searchable list of 62 icons with names
private val ICON_LIST = listOf(
    Icons.Default.Favorite to "Amor", Icons.Default.FavoriteBorder to "Cuidado", Icons.Default.MedicalServices to "Médico",
    Icons.Default.LocalHospital to "Clínica", Icons.Default.MonitorHeart to "Cardio", Icons.Default.DirectionsRun to "Deporte",
    Icons.Default.SelfImprovement to "Zen", Icons.Default.Spa to "Relajación", Icons.Default.Work to "Oficina",
    Icons.Default.BusinessCenter to "Negocios", Icons.Default.Computer to "PC", Icons.Default.Laptop to "Laptop",
    Icons.Default.Terminal to "Código", Icons.Default.TrendingUp to "Gráficas", Icons.Default.AddChart to "Ventas",
    Icons.Default.Assessment to "Reporte", Icons.Default.Book to "Libro", Icons.Default.School to "Clases",
    Icons.Default.MenuBook to "Lectura", Icons.Default.Calculate to "Cálculos", Icons.Default.Create to "Escribir",
    Icons.Default.AutoStories to "Historias", Icons.Default.Architecture to "Diseño", Icons.Default.Science to "Ciencia",
    Icons.Default.Home to "Hogar", Icons.Default.CleaningServices to "Limpieza", Icons.Default.Kitchen to "Cocina",
    Icons.Default.LocalLaundryService to "Ropa", Icons.Default.Bed to "Dormir", Icons.Default.Yard to "Jardín",
    Icons.Default.Pets to "Mascotas", Icons.Default.Lightbulb to "Idea", Icons.Default.AttachMoney to "Dinero",
    Icons.Default.CreditCard to "Tarjeta", Icons.Default.Savings to "Ahorro", Icons.Default.AccountBalance to "Banco",
    Icons.Default.AccountBalanceWallet to "Billetera", Icons.Default.ShoppingCart to "Compras", Icons.Default.Receipt to "Recibo",
    Icons.Default.LocalMall to "Tienda", Icons.Default.People to "Familia", Icons.Default.Group to "Amigos",
    Icons.Default.Handshake to "Trato", Icons.Default.Share to "Compartir", Icons.Default.Chat to "Chat",
    Icons.Default.Phone to "Llamada", Icons.Default.EmojiEmotions to "Feliz", Icons.Default.WineBar to "Brindis",
    Icons.Default.Star to "Estrella", Icons.Default.Info to "Info", Icons.Default.Build to "Herramientas",
    Icons.Default.Settings to "Ajustes", Icons.Default.Search to "Buscar", Icons.Default.Lock to "Seguridad",
    Icons.Default.Timer to "Temporizador", Icons.Default.Alarm to "Alarma", Icons.Default.CalendarMonth to "Agenda",
    Icons.Default.Notifications to "Avisos", Icons.Default.Map to "Mapa", Icons.Default.Flag to "Metas",
    Icons.Default.Task to "Tareas", Icons.Default.CheckCircle to "Completado"
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditScreen(navController: NavController, viewModel: MainViewModel, reminderId: Int?) {
    val context = LocalContext.current
    val currentLang by viewModel.language.collectAsState()
    val reminders by viewModel.reminders.collectAsState()
    val scrollState = rememberScrollState()

    // Determine if editing
    val editingReminder = remember(reminderId, reminders) {
        if (reminderId != null) reminders.find { it.id == reminderId } else null
    }

    // Input States
    var name by remember { mutableStateOf(editingReminder?.name ?: "") }
    var selectedCategory by remember { mutableStateOf(editingReminder?.category ?: "Otros") }
    
    // We map stored 24-hour hour to display hour (1-12) and AM/PM flag
    var hour24 by remember { mutableStateOf(editingReminder?.timeHour ?: 12) }
    var minute by remember { mutableStateOf(editingReminder?.timeMinute ?: 0) }
    
    val displayHour12 = remember(hour24) {
        val h = hour24 % 12
        if (h == 0) 12 else h
    }
    val isPm = remember(hour24) { hour24 >= 12 }

    var recurrence by remember { mutableStateOf(editingReminder?.recurrence ?: "DAILY") } // ONCE, DAILY, WEEKLY, MONTHLY
    var vibrate by remember { mutableStateOf(editingReminder?.vibrate ?: true) }
    var notes by remember { mutableStateOf(editingReminder?.notes ?: "") }

    // Weekly selection states
    var selectedDaysList by remember {
        mutableStateOf(
            if (editingReminder?.recurrence == "WEEKLY") {
                editingReminder.recurrenceDays.split(",").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
            } else setOf(1, 2, 3, 4, 5) // Mon-Fri default
        )
    }

    // Specific date selector for "ONCE"
    var specificDateMs by remember { mutableStateOf(editingReminder?.specificDate ?: System.currentTimeMillis()) }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    // Searchable icon selection state
    var iconSearchQuery by remember { mutableStateOf("") }
    var showIconPicker by remember { mutableStateOf(false) }

    // Dynamic Save Button Container Color
    val dynamicSaveColor by animateColorAsState(
        targetValue = if (name.isEmpty()) {
            MaterialTheme.colorScheme.primary
        } else {
            val hash = name.hashCode()
            val hue = (hash.absoluteValue % 360).toFloat()
            Color.hsv(hue, 0.55f, 0.85f)
        },
        label = "SaveFabColorAnimation"
    )

    // Interactive custom touch-and-drag clock variables
    var activeClockMode by remember { mutableStateOf("hour") } // "hour" or "minute"

    var showNotificationDialog by remember { mutableStateOf(false) }
    var showTimePastError by remember { mutableStateOf(false) }

    // Helper functions for updating time
    val updateHour12: (Int) -> Unit = { nextHour12 ->
        val h24 = if (nextHour12 == 12) {
            if (isPm) 12 else 0
        } else {
            if (isPm) nextHour12 + 12 else nextHour12
        }
        hour24 = h24
    }

    val toggleAmPm: (Boolean) -> Unit = { nextIsPm ->
        val h12 = hour24 % 12
        val h24 = if (h12 == 0) {
            if (nextIsPm) 12 else 0
        } else {
            if (nextIsPm) h12 + 12 else h12
        }
        hour24 = h24
    }

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (name.isNotEmpty()) {
                            viewModel.triggerHapticFeedback()
                            val now = Calendar.getInstance()
                            val targetCal = Calendar.getInstance().apply {
                                set(Calendar.HOUR_OF_DAY, hour24)
                                set(Calendar.MINUTE, minute)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            val isPastTime = when (recurrence) {
                                "ONCE" -> {
                                    val dateCal = Calendar.getInstance().apply { timeInMillis = specificDateMs }
                                    dateCal.set(Calendar.HOUR_OF_DAY, hour24)
                                    dateCal.set(Calendar.MINUTE, minute)
                                    dateCal.set(Calendar.SECOND, 0)
                                    dateCal.set(Calendar.MILLISECOND, 0)
                                    !dateCal.after(now)
                                }
                                "DAILY" -> !targetCal.after(now)
                                "WEEKLY" -> {
                                    val todayDay = when (now.get(Calendar.DAY_OF_WEEK)) {
                                        Calendar.MONDAY -> 1; Calendar.TUESDAY -> 2
                                        Calendar.WEDNESDAY -> 3; Calendar.THURSDAY -> 4
                                        Calendar.FRIDAY -> 5; Calendar.SATURDAY -> 6
                                        Calendar.SUNDAY -> 7; else -> 1
                                    }
                                    selectedDaysList.contains(todayDay) && !targetCal.after(now)
                                }
                                "MONTHLY" -> {
                                    now.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().apply { timeInMillis = specificDateMs }.get(Calendar.DAY_OF_MONTH) && !targetCal.after(now)
                                }
                                else -> false
                            }

                            if (isPastTime) {
                                showTimePastError = true
                                return@FloatingActionButton
                            }

                            val recurrenceDaysStr = selectedDaysList.sorted().joinToString(",")
                            val finalReminder = ReminderEntity(
                                id = editingReminder?.id ?: 0,
                                name = name,
                                category = selectedCategory,
                                timeHour = hour24,
                                timeMinute = minute,
                                recurrence = recurrence,
                                recurrenceDays = if (recurrence == "WEEKLY") recurrenceDaysStr else "1,2,3,4,5,6,7",
                                specificDate = if (recurrence == "ONCE" || recurrence == "MONTHLY") specificDateMs else null,
                                vibrate = vibrate,
                                notes = notes,
                                isActive = editingReminder?.isActive ?: true,
                                lastCompleted = editingReminder?.lastCompleted
                            )

                            if (editingReminder == null) {
                                viewModel.insertReminder(finalReminder)
                            } else {
                                viewModel.updateReminder(finalReminder)
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                            ) {
                                showNotificationDialog = true
                            } else {
                                navController.popBackStack()
                            }
                        }
                    },
                    containerColor = dynamicSaveColor,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .shadow(12.dp, RoundedCornerShape(18.dp))
                        .testTag("save_reminder_fab")
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Intention",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState)
            ) {
                // Premium Styled Header row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.35f))
                            .clickable {
                                viewModel.triggerHapticFeedback()
                                navController.popBackStack()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = if (editingReminder == null) Translations.getString("add_rem", currentLang) else Translations.getString("edit_rem", currentLang),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.width(42.dp)) // horizontal balance
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Premium Name Input card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = Translations.getString("name_intention", currentLang),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { if (it.length <= 40) name = it },
                            placeholder = { Text(Translations.getString("name_hint", currentLang), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.25f),
                                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                            ),
                            trailingIcon = {
                                Text(
                                    text = "${name.length}/40",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Premium Category & Custom Icon Selector
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Category,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${Translations.getString("category", currentLang)}: $selectedCategory",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            TextButton(
                                onClick = {
                                    viewModel.triggerHapticFeedback()
                                    showIconPicker = !showIconPicker
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (showIconPicker) Icons.Default.Close else Icons.Default.GridOn,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (showIconPicker) Translations.getString("close", currentLang) else Translations.getString("view_icons", currentLang),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // Searchable Icon Grid (Expanded)
                        AnimatedVisibility(visible = showIconPicker) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                OutlinedTextField(
                                    value = iconSearchQuery,
                                    onValueChange = { iconSearchQuery = it },
                                    placeholder = { Text(Translations.getString("search_icon", currentLang), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    singleLine = true,
                                    leadingIcon = {
                                        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                val filteredIcons = ICON_LIST.filter {
                                    it.second.lowercase().contains(iconSearchQuery.lowercase())
                                }

                                Box(
                                    modifier = Modifier
                                        .height(180.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(8.dp)
                                ) {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(6),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        items(filteredIcons) { item ->
                                            val isSelected = selectedCategory == item.second
                                            val scale by animateFloatAsState(if (isSelected) 1.15f else 1.0f)
                                            val colorBorder by animateColorAsState(
                                                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .size(44.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else Color.Transparent
                                                    )
                                                    .border(
                                                        width = 2.dp,
                                                        color = colorBorder,
                                                        shape = CircleShape
                                                    )
                                                    .clickable {
                                                        selectedCategory = item.second
                                                        viewModel.triggerHapticFeedback()
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = item.first,
                                                    contentDescription = item.second,
                                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (!showIconPicker) {
                            Spacer(modifier = Modifier.height(8.dp))
                            // Elegant preset row chips
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf("Salud", "Trabajo", "Estudio", "Hogar", "Finanzas", "Social", "Otros").forEach { cat ->
                                        val isSel = selectedCategory == cat
                                        val chipBorderColor = if (isSel) {
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                        } else {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                                        }
                                        FilterChip(
                                            selected = isSel,
                                            onClick = {
                                                selectedCategory = cat
                                                viewModel.triggerHapticFeedback()
                                            },
                                            label = {
                                                Text(
                                                    text = Translations.getString("category_${cat.lowercase()}", currentLang),
                                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                                                )
                                            },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                                selectedLabelColor = MaterialTheme.colorScheme.primary,
                                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                                labelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                            ),
                                            border = FilterChipDefaults.filterChipBorder(
                                                enabled = true,
                                                selected = isSel,
                                                borderColor = chipBorderColor,
                                                selectedBorderColor = MaterialTheme.colorScheme.primary
                                            )
                                        )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Premium Custom Time Selector Section (REDESIGNED)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title / Header for Time
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = Translations.getString("define_time", currentLang),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            
                            // Interactive Hour/Minute Toggle Pill
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(4.dp)
                            ) {
                                val hourContainerColor by animateColorAsState(
                                    if (activeClockMode == "hour") MaterialTheme.colorScheme.primary else Color.Transparent
                                )
                                val hourTextColor by animateColorAsState(
                                    if (activeClockMode == "hour") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(hourContainerColor)
                                        .clickable {
                                            activeClockMode = "hour"
                                            viewModel.triggerHapticFeedback()
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = Translations.getString("hour", currentLang),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = hourTextColor
                                    )
                                }

                                val minuteContainerColor by animateColorAsState(
                                    if (activeClockMode == "minute") MaterialTheme.colorScheme.primary else Color.Transparent
                                )
                                val minuteTextColor by animateColorAsState(
                                    if (activeClockMode == "minute") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(minuteContainerColor)
                                        .clickable {
                                            activeClockMode = "minute"
                                            viewModel.triggerHapticFeedback()
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = Translations.getString("minute", currentLang),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = minuteTextColor
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // High-End Glowing Digital Time display with Micro-Controllers
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Hour micro-controller
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    onClick = {
                                        var next = displayHour12 + 1
                                        if (next > 12) next = 1
                                        updateHour12(next)
                                        viewModel.triggerHapticFeedback()
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Sube hora", tint = MaterialTheme.colorScheme.primary)
                                }
                                
                                val activeHourBorder = if (activeClockMode == "hour") {
                                    androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                                } else {
                                    androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                }
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (activeClockMode == "hour") {
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                        } else {
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
                                        }
                                    ),
                                    border = activeHourBorder,
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clickable {
                                            activeClockMode = "hour"
                                            viewModel.triggerHapticFeedback()
                                        }
                                ) {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = String.format("%02d", displayHour12),
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (activeClockMode == "hour") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        var prev = displayHour12 - 1
                                        if (prev < 1) prev = 12
                                        updateHour12(prev)
                                        viewModel.triggerHapticFeedback()
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Baja hora", tint = MaterialTheme.colorScheme.primary)
                                }
                            }

                            // Glowing divider colon
                            Text(
                                text = ":",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                modifier = Modifier.padding(horizontal = 14.dp)
                            )

                            // Minute micro-controller
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                IconButton(
                                    onClick = {
                                        minute = (minute + 1) % 60
                                        viewModel.triggerHapticFeedback()
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Sube minuto", tint = MaterialTheme.colorScheme.primary)
                                }

                                val activeMinuteBorder = if (activeClockMode == "minute") {
                                    androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                                } else {
                                    androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                }
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (activeClockMode == "minute") {
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                        } else {
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
                                        }
                                    ),
                                    border = activeMinuteBorder,
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clickable {
                                            activeClockMode = "minute"
                                            viewModel.triggerHapticFeedback()
                                        }
                                ) {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = String.format("%02d", minute),
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (activeClockMode == "minute") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        minute = if (minute - 1 < 0) 59 else minute - 1
                                        viewModel.triggerHapticFeedback()
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Baja minuto", tint = MaterialTheme.colorScheme.primary)
                                }
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            // Sliding AM/PM Segmented control
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val amColor by animateColorAsState(
                                    if (!isPm) MaterialTheme.colorScheme.primary else Color.Transparent
                                )
                                val amTextColor by animateColorAsState(
                                    if (!isPm) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(amColor)
                                        .clickable {
                                            toggleAmPm(false)
                                            viewModel.triggerHapticFeedback()
                                        }
                                        .padding(horizontal = 14.dp, vertical = 10.dp)
                                ) {
                                    Text("AM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = amTextColor)
                                }

                                val pmColor by animateColorAsState(
                                    if (isPm) MaterialTheme.colorScheme.primary else Color.Transparent
                                )
                                val pmTextColor by animateColorAsState(
                                    if (isPm) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(pmColor)
                                        .clickable {
                                            toggleAmPm(true)
                                            viewModel.triggerHapticFeedback()
                                        }
                                        .padding(horizontal = 14.dp, vertical = 10.dp)
                                ) {
                                    Text("PM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = pmTextColor)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // High fidelity interactive Dial Selector face
                        val dialModeThemeColor = MaterialTheme.colorScheme.primary
                        val dialModeOnThemeColor = MaterialTheme.colorScheme.onSurface
                        
                        Box(
                            modifier = Modifier
                                .size(210.dp)
                                .pointerInput(activeClockMode, isPm) {
                                    detectTapGestures { offset ->
                                        updateTimeFromDial(offset, size.width, size.height, activeClockMode) { h, m ->
                                            if (h != null) updateHour12(h)
                                            if (m != null) minute = m
                                            viewModel.triggerHapticFeedback()
                                        }
                                    }
                                }
                                .pointerInput(activeClockMode, isPm) {
                                    detectDragGestures { change, _ ->
                                        updateTimeFromDial(change.position, size.width, size.height, activeClockMode) { h, m ->
                                            if (h != null) updateHour12(h)
                                            if (m != null) minute = m
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(190.dp)) {
                                val center = Offset(size.width / 2, size.height / 2)
                                val radius = size.width / 2

                                // Background Circle Dial face
                                drawCircle(
                                    color = dialModeOnThemeColor.copy(alpha = 0.05f),
                                    radius = radius,
                                    center = center
                                )
                                
                                // Subtle inner dial circle
                                drawCircle(
                                    color = dialModeOnThemeColor.copy(alpha = 0.02f),
                                    radius = radius * 0.7f,
                                    center = center
                                )

                                // Pointer lines
                                val angleDeg = if (activeClockMode == "hour") {
                                    (displayHour12 % 12) * 30.0
                                } else {
                                    minute * 6.0
                                }
                                val angleRad = Math.toRadians(angleDeg)
                                val lineEndX = (center.x + (radius - 22.dp.toPx()) * sin(angleRad)).toFloat()
                                val lineEndY = (center.y - (radius - 22.dp.toPx()) * cos(angleRad)).toFloat()

                                // Soft neon glow behind the pointer line
                                drawLine(
                                    color = dialModeThemeColor.copy(alpha = 0.15f),
                                    start = center,
                                    end = Offset(lineEndX, lineEndY),
                                    strokeWidth = 8.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                                
                                drawLine(
                                    color = dialModeThemeColor,
                                    start = center,
                                    end = Offset(lineEndX, lineEndY),
                                    strokeWidth = 3.dp.toPx(),
                                    cap = StrokeCap.Round
                                )

                                // Center pin
                                drawCircle(
                                    color = dialModeThemeColor,
                                    radius = 6.dp.toPx(),
                                    center = center
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 2.dp.toPx(),
                                    center = center
                                )

                                // Dial Numbers / Markers (Gives premium luxury aesthetic)
                                val numCount = 12
                                val paint = Paint().apply {
                                    color = dialModeOnThemeColor.copy(alpha = 0.7f).toArgb()
                                    textSize = 10.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                                }

                                for (i in 1..12) {
                                    val itemRad = Math.toRadians((i * 30).toDouble())
                                    // Number position slightly closer inwards
                                    val numX = (center.x + (radius - 22.dp.toPx()) * sin(itemRad)).toFloat()
                                    val numY = (center.y - (radius - 22.dp.toPx()) * cos(itemRad)).toFloat()
                                    
                                    val isCurrent = if (activeClockMode == "hour") {
                                        displayHour12 == i
                                    } else {
                                        (minute / 5 == i % 12)
                                    }

                                    if (isCurrent) {
                                        drawCircle(
                                            color = dialModeThemeColor.copy(alpha = 0.25f),
                                            radius = 12.dp.toPx(),
                                            center = Offset(numX, numY - 3.dp.toPx())
                                        )
                                        paint.color = dialModeThemeColor.toArgb()
                                    } else {
                                        paint.color = dialModeOnThemeColor.copy(alpha = 0.45f).toArgb()
                                    }

                                    // Display Hours (1..12) or Minutes (0, 5, 10... 55)
                                    val labelText = if (activeClockMode == "hour") {
                                        i.toString()
                                    } else {
                                        val minVal = (i * 5) % 60
                                        String.format("%02d", minVal)
                                    }

                                    drawContext.canvas.nativeCanvas.drawText(
                                        labelText,
                                        numX,
                                        numY + 4.dp.toPx(), // Adjust baseline vertical centering
                                        paint
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Premium Recurrence Selector Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Repeat,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = Translations.getString("recurrence", currentLang),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("ONCE", "DAILY", "WEEKLY", "MONTHLY").forEach { rec ->
                                val isSelected = recurrence == rec
                                val localized = when (rec) {
                                    "ONCE" -> Translations.getString("rec_once", currentLang)
                                    "DAILY" -> Translations.getString("rec_daily", currentLang)
                                    "WEEKLY" -> Translations.getString("rec_weekly", currentLang)
                                    else -> Translations.getString("rec_monthly", currentLang)
                                }
                                val chipBorderColor = if (isSelected) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                                }
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        recurrence = rec
                                        viewModel.triggerHapticFeedback()
                                    },
                                    label = {
                                        Text(
                                            text = localized,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                        selectedLabelColor = MaterialTheme.colorScheme.primary,
                                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                        labelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = isSelected,
                                        borderColor = chipBorderColor,
                                        selectedBorderColor = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        // Weekly Custom Days Row
                        if (recurrence == "WEEKLY") {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = Translations.getString("selected_days", currentLang),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val initials = when (currentLang) {
                                    com.example.ui.translation.AppLanguage.ES -> listOf("L", "M", "M", "J", "V", "S", "D")
                                    com.example.ui.translation.AppLanguage.EN -> listOf("M", "T", "W", "T", "F", "S", "S")
                                    com.example.ui.translation.AppLanguage.PT -> listOf("S", "T", "Q", "Q", "S", "S", "D")
                                    com.example.ui.translation.AppLanguage.HI -> listOf("सो", "मं", "बु", "गु", "शु", "श", "र")
                                    com.example.ui.translation.AppLanguage.ZH -> listOf("一", "二", "三", "四", "五", "六", "日")
                                    com.example.ui.translation.AppLanguage.AR -> listOf("ح", "ن", "ث", "أ", "خ", "ج", "س")
                                    com.example.ui.translation.AppLanguage.FR -> listOf("L", "M", "M", "J", "V", "S", "D")
                                }

                                (1..7).forEach { dayNum ->
                                    val isSelected = selectedDaysList.contains(dayNum)
                                    val dayBgColor by animateColorAsState(
                                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface.copy(alpha = 0.25f)
                                    )
                                    val dayTextColor by animateColorAsState(
                                        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(dayBgColor)
                                            .clickable {
                                                selectedDaysList = if (isSelected) {
                                                    selectedDaysList - dayNum
                                                } else {
                                                    selectedDaysList + dayNum
                                                }
                                                viewModel.triggerHapticFeedback()
                                            }
                                            .border(
                                                width = 1.dp,
                                                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = initials[dayNum - 1],
                                            color = dayTextColor,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // Specific Date Selector for ONCE
                        if (recurrence == "ONCE") {
                            Spacer(modifier = Modifier.height(14.dp))
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Fecha: ${formatter.format(Date(specificDateMs))}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                    )
                                }
                                TextButton(
                                    onClick = {
                                        viewModel.triggerHapticFeedback()
                                        showDatePickerDialog = true
                                    }
                                ) {
                                    Text(
                                        text = Translations.getString("choose_date", currentLang),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Premium Vibration Toggle and Notes
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Vibration,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = Translations.getString("vibrate", currentLang),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Switch(
                                checked = vibrate,
                                onCheckedChange = {
                                    vibrate = it
                                    viewModel.triggerHapticFeedback()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Note,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = Translations.getString("notes_intention", currentLang),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            placeholder = { Text(Translations.getString("notes_hint", currentLang), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            maxLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.25f),
                                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(110.dp)) // Safe space for scrolling under floating FAB
            }
        }
    }

    // Compose Date Picker Dialog for once recurrence
    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = specificDateMs)
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        specificDateMs = datePickerState.selectedDateMillis ?: specificDateMs
                        showDatePickerDialog = false
                    }
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text(Translations.getString("cancel", currentLang))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Time in past error dialog
    if (showTimePastError) {
        AlertDialog(
            onDismissRequest = { showTimePastError = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = Translations.getString("app_name", currentLang),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(text = Translations.getString("time_past_error", currentLang))
            },
            confirmButton = {
                TextButton(onClick = { showTimePastError = false }) {
                    Text(
                        text = Translations.getString("ok", currentLang),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }

    // Notification permission dialog
    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = {
                showNotificationDialog = false
                navController.popBackStack()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text(
                    text = Translations.getString("notification_dialog_title", currentLang),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = Translations.getString("notification_dialog_message", currentLang)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showNotificationDialog = false
                        openAppNotificationSettings(context)
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = Translations.getString("notification_dialog_go", currentLang),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showNotificationDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text(Translations.getString("notification_dialog_not_now", currentLang))
                }
            }
        )
    }
}

// Convert touch point to hours or minutes (Mapped to 1-12 values clockwise)
private fun updateTimeFromDial(
    offset: Offset,
    width: Int,
    height: Int,
    mode: String,
    onResult: (Int?, Int?) -> Unit
) {
    val centerX = width / 2.0
    val centerY = height / 2.0
    val dx = offset.x - centerX
    val dy = offset.y - centerY

    var angleRad = calculateAngle(offset, centerX, centerY)
    val angleDeg = Math.toDegrees(angleRad)

    if (mode == "hour") {
        val calculatedHour = calculateHourFromAngle(angleDeg)
        onResult(calculatedHour, null)
    } else {
        val calculatedMinute = calculateMinuteFromAngle(angleDeg)
        onResult(null, calculatedMinute)
    }
}

private fun calculateAngle(offset: Offset, centerX: Double, centerY: Double): Double {
    val dx = offset.x - centerX
    val dy = offset.y - centerY
    var angleRad = atan2(dx, -dy)
    if (angleRad < 0) {
        angleRad += 2 * Math.PI
    }
    return angleRad
}

private fun calculateHourFromAngle(angleDeg: Double): Int {
    // 12 hours in 360 deg -> 30 deg per hour
    val calculatedHour = ((angleDeg + 15) % 360 / 30).toInt()
    return if (calculatedHour == 0) 12 else calculatedHour
}

private fun calculateMinuteFromAngle(angleDeg: Double): Int {
    // 60 minutes in 360 deg -> 6 deg per minute, snapped to nearest 5 minutes
    return (((angleDeg + 15) % 360 / 30).toInt() * 5) % 60
}

private fun openAppNotificationSettings(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    } else {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}
