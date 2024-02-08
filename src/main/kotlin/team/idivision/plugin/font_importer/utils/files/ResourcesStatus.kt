package team.idivision.plugin.font_importer.utils.files

sealed class ResourcesStatus {
    object NoResDir : ResourcesStatus()
    object NoFontDir : ResourcesStatus()
    object Success : ResourcesStatus()
}