package team.idivision.plugin.font_importer.utils.files

sealed class ResourcesStatus {
    data object NoResDir : ResourcesStatus()
    data object NoFontDir : ResourcesStatus()
    data object Success : ResourcesStatus()
}