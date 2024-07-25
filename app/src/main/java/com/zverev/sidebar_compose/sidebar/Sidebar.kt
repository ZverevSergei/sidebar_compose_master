package com.zverev.sidebar_compose.sidebar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zverev.sidebar_compose.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun Sidebar() {
    val sidebarItem = listOf(
        SidebarItems(icon = Icons.Default.Face, text = "Profile", badgeCount = 0, hasBadge = true),
        SidebarItems(Icons.Filled.Email, "Inbox", 32, true),
        SidebarItems(Icons.Filled.Favorite, "Favorite", 512, true),
        SidebarItems(Icons.Filled.Settings, "Setting", 0, true)
    )
    val drawerItem2 = listOf(
        SidebarItems(Icons.Default.Share, "Share", 0, false),
        SidebarItems(Icons.Filled.Star, "Rate", 8, true)
    )
    var selectedItem by remember {
        mutableStateOf(sidebarItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxSize()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.emoji),
                            contentDescription = "profile",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(shape = CircleShape)
                        )
                        Text(
                            text = "Email",
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Divider(
                        Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
                        Color.DarkGray
                    )

                }
                LazyColumn {
                    itemsIndexed(sidebarItem) { index, items ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = items.text
                                )
                            },
                            selected = items == selectedItem,
                            onClick = {
                                selectedItem = items

                                scope.launch {
                                    drawerState.close()
                                }

                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            icon = {
                                Icon(imageVector = items.icon, contentDescription = items.text)
                            },
                            badge = {
                                if (items.hasBadge) {
                                    if (items.badgeCount > 0) {
                                        BadgedBox(
                                            badge = {
                                                Badge {
                                                    Text(
                                                        text = items.badgeCount.toString(),
                                                        fontSize = 17.sp
                                                    )
                                                }
                                            }
                                        ) {
                                            Icon(Icons.Default.Check, contentDescription = "Check")
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
                LazyColumn {
                    itemsIndexed(drawerItem2) { index, items ->
                        NavigationDrawerItem(label = { Text(text = items.text) },
                            selected = items == selectedItem,
                            onClick = {
                                selectedItem = items

                                scope.launch {
                                    drawerState.close()
                                }

                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
                                Icon(imageVector = items.icon, contentDescription = items.text)
                            }
                        )
                    }
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                },
                title = { },
            )
        })
        { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Text(text = "Open sidebar")
                }
            }
        }
    }
}