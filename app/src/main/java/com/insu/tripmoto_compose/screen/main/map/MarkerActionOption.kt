package com.insu.tripmoto_compose.screen.main.map

enum class MarkerActionOption(val title: String) {
    EditMarker("Edit"),
    DeleteMarker("Delete");

    companion object {
        fun getByTitle(title: String): MarkerActionOption {
            values().forEach {
                    action -> if(title == action.title) return action
            }

            return EditMarker

        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            values().forEach { markerAction ->
                if(hasEditOption || markerAction != EditMarker) {
                    options.add(markerAction.title)
                }
            }
            return options
        }
    }
}