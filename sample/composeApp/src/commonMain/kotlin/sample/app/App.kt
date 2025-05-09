package sample.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oriooneee.LazyColumnWithStickyItem


@Composable
fun NameItem(
    position: Int,
    name: String,
    color: Color
) {
    ListItem(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
        ,
        colors = ListItemDefaults.colors(containerColor = color),
        headlineContent = {
            Text(
                text = name,

            )
        },
        leadingContent = {
            Text(
                text = "$position)",
            )
        }
    )
}

@Composable
fun App() {
    Scaffold {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            val names = listOf(
                "John Doe",
                "Jane Smith",
                "Michael Johnson",
                "Emily Davis",
                "Robert Brown",
                "Linda Wilson",
                "William Jones",
                "Elizabeth Taylor",
                "David Miller",
                "Jennifer Anderson",
                "Richard Thomas",
                "Mary Jackson",
                "Joseph White",
                "Susan Harris",
                "Charles Martin",
                "Jessica Thompson",
                "Christopher Garcia",
                "Karen Martinez",
                "Daniel Robinson",
                "Nancy Clark",
                "Matthew Rodriguez",
                "Lisa Lewis",
                "Anthony Lee",
                "Betty Walker",
                "Mark Hall",
                "Margaret Allen",
                "Donald Young",
                "Sandra Hernandez",
                "Steven King",
                "Ashley Wright",
                "Paul Lopez",
                "Kimberly Hill",
                "Andrew Scott",
                "Donna Green",
                "Joshua Adams",
                "Carol Baker",
                "Kenneth Gonzalez",
                "Michelle Nelson",
                "Kevin Carter",
                "Dorothy Mitchell",
                "Brian Perez",
                "Amanda Roberts",
                "George Turner",
                "Melissa Phillips",
                "Edward Campbell",
                "Deborah Parker",
                "Ronald Evans",
                "Stephanie Edwards",
                "Timothy Collins",
                "Rebecca Stewart",
                "Jason Sanchez",
                "Laura Morris",
                "Jeffrey Rogers",
                "Cynthia Reed",
                "Ryan Cook",
                "Kathleen Morgan",
                "Gary Bell",
                "Amy Murphy",
                "Jacob Bailey",
                "Shirley Rivera",
                "Nicholas Cooper",
                "Angela Richardson",
                "Eric Cox",
                "Brenda Howard",
                "Stephen Ward",
                "Pamela Torres",
                "Larry Peterson",
                "Katherine Gray",
                "Justin Ramirez",
                "Ruth James",
                "Scott Watson"
            )

            val stickyItem = "Carol Baker"
            val stickyItemIndex = names.indexOf(stickyItem)


            LazyColumnWithStickyItem(
                stickyItemIndex = stickyItemIndex,
                items = names,
                itemUI = { item, index, isSticky, _ ->
                    NameItem(
                        position = index,
                        name = item,
                        color = if (isSticky) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            Color.Transparent
                        }
                    )
                },
            )
        }
    }
}