package com.example.android_base_compose.main.ui.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.android_base_compose.R
import com.example.android_base_compose.base.api.entities.UserResponse
import com.example.android_base_compose.base.ui.common.modifier16Padding
import com.example.android_base_compose.base.ui.common.modifier8Padding
import com.example.android_base_compose.main.theme.Dimen

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val listUser = viewModel.userList.collectAsState().value
    Column(Modifier.padding(top = Dimen.Space16)) {
        Text(
            text = stringResource(id = R.string.home_screen_title),
            modifier = modifier16Padding,
            textAlign = TextAlign.Center,
            fontSize = Dimen.Text20,
            fontWeight = FontWeight.Bold,
        )
        LazyColumn {
            items(listUser) { cardResponse ->
                ItemUserList(cardResponse)
            }
        }
    }
}

@Composable
fun ItemUserList(userResponse: UserResponse) {
    Row(
        Modifier
            .padding(horizontal = Dimen.Space16)
            .padding(bottom = Dimen.Space16)
            .background(Color.Unspecified)
            .fillMaxWidth()
            .border(
                BorderStroke(Dimen.Space1, color = Color.LightGray),
                shape = RoundedCornerShape(Dimen.Space8)
            )


    ) {
        AsyncImage(
            model = userResponse.avatar,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(Dimen.Space8)
                .size(Dimen.Space100),
        )
        Column {
            Text(
                textAlign = TextAlign.Start,
                fontSize = Dimen.Text16,
                text = userResponse.name ?: "",
                fontWeight = FontWeight.Bold,
                modifier = modifier8Padding.padding(top = Dimen.Space8),
                maxLines = 1,
                softWrap = true
            )
            Text(
                modifier = modifier8Padding.padding(top = Dimen.Space8),
                textAlign = TextAlign.Start,
                text = userResponse.createdAt ?: ""
            )
        }
    }
}
