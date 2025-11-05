package com.example.conventionalcommits

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.ItemEvent
import javax.swing.*

class CommitWizardAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = CommitWizardDialog()
        if (dialog.showAndGet()) {
            val message = dialog.buildCommitMessage()

            val commitPanel = e.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL)
            if (commitPanel is CommitMessageI) {
                commitPanel.setCommitMessage(message)
            }
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL) != null
        e.presentation.icon = IconLoader.getIcon("/icons/ccw-nerdwarts-logo-transparent-16", javaClass)
        e.presentation.text = "Conventional Commit Wizard"
    }
}

data class CommitType(val name: String, val defaultEmoji: String)
data class EmojiOption(val name: String, val emoji: String, val description: String) {
    override fun toString(): String = if (emoji.isEmpty()) name else ":$name: $emoji"
}

class EmojiListCellRenderer : DefaultListCellRenderer() {
    override fun getListCellRendererComponent(
        list: JList<*>?,
        value: Any?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val panel = JPanel(BorderLayout()).apply {
            border = JBUI.Borders.empty(4, 6)
        }

        if (value is EmojiOption) {
            val mainLabel = JLabel(if (value.emoji.isEmpty()) value.name else "${value.emoji} :${value.name}:")
            val descLabel = JLabel("<html><font size='-2' color='gray'>${value.description}</font></html>")

            panel.add(mainLabel, BorderLayout.NORTH)
            panel.add(descLabel, BorderLayout.SOUTH)

            if (isSelected) {
                panel.background = list?.selectionBackground
                mainLabel.foreground = list?.selectionForeground
            } else {
                panel.background = list?.background
                mainLabel.foreground = list?.foreground
            }
        }

        return panel
    }
}

class CommitWizardDialog : DialogWrapper(true) {
    private val commitTypes = arrayOf(
        CommitType("feat", "✨"),
        CommitType("fix", "🐛"),
        CommitType("docs", "📝"),
        CommitType("style", "💄"),
        CommitType("refactor", "♻️"),
        CommitType("perf", "⚡"),
        CommitType("test", "✅"),
        CommitType("build", "👷"),
        CommitType("ci", "💚"),
        CommitType("chore", "🔧"),
        CommitType("revert", "⏪")
    )

    private val emojis = arrayOf(
        // Most commonly used (top 10)
        EmojiOption("None", "", "No gitmoji."),
        EmojiOption("sparkles", "✨", "Introduce new features."),
        EmojiOption("bug", "🐛", "Fix a bug."),
        EmojiOption("memo", "📝", "Add or update documentation."),
        EmojiOption("recycle", "♻️", "Refactor code."),
        EmojiOption("lipstick", "💄", "Add or update the UI and style files."),
        EmojiOption("zap", "⚡", "Improve performance."),
        EmojiOption("white_check_mark", "✅", "Add, update, or pass tests."),
        EmojiOption("wrench", "🔧", "Add or update configuration files."),
        EmojiOption("construction", "🚧", "Work in progress."),

        // Features & Changes
        EmojiOption("tada", "🎉", "Begin a project."),
        EmojiOption("boom", "💥", "Introduce breaking changes."),
        EmojiOption("fire", "🔥", "Remove code or files."),
        EmojiOption("truck", "🚚", "Move or rename resources (e.g. files, paths, routes)."),

        // Bug Fixes & Patches
        EmojiOption("ambulance", "🚑", "Critical hotfix."),
        EmojiOption("adhesive_bandage", "🩹", "Simple fix for a non-critical issue."),
        EmojiOption("rotating_light", "🚨", "Fix compiler / linter warnings."),
        EmojiOption("goal_net", "🥅", "Catch errors."),

        // Code Quality & Structure
        EmojiOption("art", "🎨", "Improve structure / format of the code."),
        EmojiOption("pencil2", "✏️", "Fix typos."),
        EmojiOption("poop", "💩", "Write bad code that needs to be improved."),
        EmojiOption("coffin", "⚰️", "Remove dead code."),
        EmojiOption("wastebasket", "🗑️", "Deprecate code that needs to be cleaned up."),
        EmojiOption("bulb", "💡", "Add or update comments in source code."),

        // Testing
        EmojiOption("test_tube", "🧪", "Add a failing test."),
        EmojiOption("alembic", "⚗️", "Perform experiments."),
        EmojiOption("camera_flash", "📸", "Add or update snapshots."),
        EmojiOption("clown_face", "🤡", "Mock things."),

        // Dependencies
        EmojiOption("arrow_up", "⬆️", "Upgrade dependencies."),
        EmojiOption("arrow_down", "⬇️", "Downgrade dependencies."),
        EmojiOption("heavy_plus_sign", "➕", "Add a dependency."),
        EmojiOption("heavy_minus_sign", "➖", "Remove a dependency."),
        EmojiOption("pushpin", "📌", "Pin dependencies to specific versions."),
        EmojiOption("package", "📦", "Add or update compiled files or packages."),

        // CI/CD & Build
        EmojiOption("construction_worker", "👷", "Add or update CI build system."),
        EmojiOption("green_heart", "💚", "Fix CI Build."),
        EmojiOption("rocket", "🚀", "Deploy stuff."),
        EmojiOption("hammer", "🔨", "Add or update development scripts."),

        // Security
        EmojiOption("lock", "🔒", "Fix security issues."),
        EmojiOption("closed_lock_with_key", "🔐", "Add or update secrets."),
        EmojiOption("passport_control", "🛂", "Work on code related to authorization, roles and permissions."),

        // Version Control
        EmojiOption("bookmark", "🔖", "Release / Version tags."),
        EmojiOption("rewind", "⏪", "Revert changes."),
        EmojiOption("twisted_rightwards_arrows", "🔀", "Merge branches."),

        // Database
        EmojiOption("card_file_box", "🗃️", "Perform database related changes."),
        EmojiOption("seedling", "🌱", "Add or update seed files."),

        // Logging & Monitoring
        EmojiOption("loud_sound", "🔊", "Add or update logs."),
        EmojiOption("mute", "🔇", "Remove logs."),
        EmojiOption("chart_with_upwards_trend", "📈", "Add or update analytics or track code."),
        EmojiOption("stethoscope", "🩺", "Add or update healthcheck."),

        // UI/UX & Accessibility
        EmojiOption("children_crossing", "🚸", "Improve user experience / usability."),
        EmojiOption("wheelchair", "♿", "Improve accessibility."),
        EmojiOption("dizzy", "💫", "Add or update animations and transitions."),
        EmojiOption("bento", "🍱", "Add or update assets."),
        EmojiOption("iphone", "📱", "Work on responsive design."),

        // Internationalization
        EmojiOption("globe_with_meridians", "🌐", "Internationalization and localization."),
        EmojiOption("speech_balloon", "💬", "Add or update text and literals."),

        // Architecture & Infrastructure
        EmojiOption("building_construction", "🏗️", "Make architectural changes."),
        EmojiOption("bricks", "🧱", "Infrastructure related changes."),
        EmojiOption("technologist", "🧑‍💻", "Improve developer experience."),
        EmojiOption("necktie", "👔", "Add or update business logic."),

        // External APIs & Integration
        EmojiOption("alien", "👽", "Update code due to external API changes."),

        // Documentation & Licensing
        EmojiOption("page_facing_up", "📄", "Add or update license."),

        // Feature Flags & Configuration
        EmojiOption("triangular_flag_on_post", "🚩", "Add, update, or remove feature flags."),
        EmojiOption("label", "🏷️", "Add or update types."),

        // SEO & Metadata
        EmojiOption("mag", "🔍", "Improve SEO."),

        // Contributors & Collaboration
        EmojiOption("busts_in_silhouette", "👥", "Add or update contributor(s)."),
        EmojiOption("beers", "🍻", "Write code drunkenly."),

        // Git & Version Control Files
        EmojiOption("see_no_evil", "🙈", "Add or update a .gitignore file."),

        // Fun & Easter Eggs
        EmojiOption("egg", "🥚", "Add or update an easter egg."),

        // Data & Analysis
        EmojiOption("monocle_face", "🧐", "Data exploration/inspection."),

        // Concurrency & Performance
        EmojiOption("thread", "🧵", "Add or update code related to multithreading or concurrency."),

        // Validation & Safety
        EmojiOption("safety_vest", "🦺", "Add or update code related to validation."),

        // Sponsorship
        EmojiOption("money_with_wings", "💸", "Add sponsorships or money related infrastructure.")
    )

    private val typeField = JComboBox(commitTypes.map { it.name }.toTypedArray())
    private val emojiField = JComboBox(emojis).apply {
        renderer = EmojiListCellRenderer()
        maximumRowCount = 10
    }
    private val scopeField = JTextField()
    private val descriptionField = JTextField()
    private val bodyField = JTextArea(10, 100)
    private val footerField = JTextArea(4, 100)
    private val breakingChangeCheckbox = com.intellij.ui.components.JBCheckBox("Breaking Change")

    init {
        init()
        title = "Conventional Commit Wizard"

        updateEmojiForType(typeField.selectedItem as String)

        typeField.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                updateEmojiForType(event.item as String)
            }
        }
    }

    private fun updateEmojiForType(typeName: String) {
        val defaultEmoji = commitTypes.find { it.name == typeName }?.defaultEmoji ?: ""
        val emojiIndex = emojis.indexOfFirst { it.emoji == defaultEmoji }
        if (emojiIndex >= 0) {
            emojiField.selectedIndex = emojiIndex
        }
    }

    override fun createCenterPanel(): JComponent = panel {
        row("Type:") {
            cell(typeField)
                .comment("Select the type of change")
        }
        row("Emoji:") {
            cell(emojiField)
                .comment("Select an emoji for the commit")
        }
        row("Scope:") {
            cell(scopeField)
                .comment("Optional: affected scope (e.g., component name)")
                .columns(50)
        }
        row("Description:") {
            cell(descriptionField)
                .comment("Short description of the change")
                .focused()
                .columns(50)
        }
        row {
            cell(breakingChangeCheckbox)
                .comment("Check if this is a breaking change")
        }
        row("Body:") {
            scrollCell(bodyField)
                .comment("Optional: detailed explanation")
        }
        row("Footer:") {
            scrollCell(footerField)
                .comment("Optional: references (e.g., fixes #123)")
        }
    }

    fun buildCommitMessage(): String {
        val type = typeField.selectedItem
        val scope = scopeField.text.trim().takeIf { it.isNotBlank() }?.let { "($it)" } ?: ""
        val breaking = if (breakingChangeCheckbox.isSelected) "!" else ""

        val selectedEmoji = emojiField.selectedItem as EmojiOption
        val emojiPart = if (selectedEmoji.emoji.isNotEmpty()) " ${selectedEmoji.emoji}" else ""

        val desc = descriptionField.text.trim()
        val body = bodyField.text.trim()
        val footer = footerField.text.trim()

        return buildString {
            append("$type$scope$breaking:$emojiPart $desc")
            if (body.isNotEmpty()) {
                append("\n\n$body")
            }
            if (breakingChangeCheckbox.isSelected && !body.contains("BREAKING CHANGE:")) {
                append("\n\nBREAKING CHANGE: ")
            }
            if (footer.isNotEmpty()) {
                append("\n\n$footer")
            }
        }
    }
}