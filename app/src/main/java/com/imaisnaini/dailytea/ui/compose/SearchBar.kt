package com.imaisnaini.dailytea.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import com.imaisnaini.dailytea.ui.theme.BorderColor
import com.imaisnaini.dailytea.ui.theme.PrimaryBlue
import com.imaisnaini.dailytea.ui.theme.SurfaceLight
import com.imaisnaini.dailytea.ui.theme.TextPrimary
import com.imaisnaini.dailytea.ui.theme.TextSecondary
import com.imaisnaini.dailytea.ui.theme.Typography
import com.imaisnaini.dailytea.ui.theme.border_light
import com.imaisnaini.dailytea.ui.theme.icon_small
import com.imaisnaini.dailytea.ui.theme.padding_large
import com.imaisnaini.dailytea.ui.theme.padding_normal
import com.imaisnaini.dailytea.ui.theme.radius_xlarge

@Composable
fun SearchBar(
    query: String?,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Find you tea..",
    buttonText: String = "Search",
    enabled: Boolean = true
) {
    val pillShape = RoundedCornerShape(radius_xlarge)
    val focusManager = LocalFocusManager.current

    Surface(
        shape = pillShape,
        color = SurfaceLight,
        border = BorderStroke(border_light, BorderColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = padding_large,
                    end = padding_normal,
                    top = padding_normal,
                    bottom = padding_normal
                )
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = buttonText,
                tint = PrimaryBlue,
                modifier = Modifier.size(icon_small)
            )
            Spacer(modifier = Modifier.width(padding_normal))
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
            ) {
                if (query!!.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = Typography.body2,
                        color = TextSecondary
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    enabled = enabled,
                    singleLine = true,
                    textStyle = Typography.body2.copy(
                        color = TextPrimary
                    ),
                    cursorBrush = SolidColor(PrimaryBlue),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}