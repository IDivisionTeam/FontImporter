package team.uptech.fontimporter.utils

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import team.uptech.fontimporter.localization.Localization


fun buildImportSuccessNotification(project: Project?, importedFontsCount: Int, replacedFontsCount: Int, path: String) {
    var message = ""

    if (replacedFontsCount > 0) {
        message = Localization.getString("notification.message.fonts_replaced", replacedFontsCount)
    }

    if (importedFontsCount > 0) {
        message = message.plus(
            Localization.getString("notification.message.fonts_imported", importedFontsCount)
                .run { if (replacedFontsCount > 0) ", ${this.toLowerCase()}" else this })
    }

    val location = " ${Localization.getString("notification.message.part.in").plus(" $path")}"

    NotificationGroupManager.getInstance().getNotificationGroup("team.uptech.fontimporter.ImportSuccess")
        .createNotification(message.plus(location).trim(), NotificationType.INFORMATION).notify(project)
}

fun buildDeleteSuccessNotification(project: Project?, message: String) {

    NotificationGroupManager.getInstance().getNotificationGroup("team.uptech.fontimporter.RemoveSuccess")
        .createNotification(message, NotificationType.INFORMATION).notify(project)
}

fun buildExceptionNotification(project: Project?, error: String) {

    NotificationGroupManager.getInstance().getNotificationGroup("team.uptech.fontimporter.ImportError")
        .createNotification(error, NotificationType.ERROR).notify(project)
}