# 🧙‍♂️ Conventional Commits Wizard

A powerful IntelliJ IDEA plugin that helps you write better commit messages using Conventional Commits and Gitmoji
standards.

## ✨ Features

- **Gitmoji Support**: Browse and select from a comprehensive list of gitmojis to make your commits more expressive
- **Conventional Commits**: Enforces the Conventional Commits specification
- **Intuitive UI**: Easy-to-use wizard interface for crafting commit messages
- **Type Safety**: Predefined commit types (feat, fix, chore, docs, etc.)
- **Scope Support**: Add optional scopes to your commits
- **Breaking Changes**: Mark breaking changes with proper formatting

## 🚀 Installation

### From JetBrains Marketplace

1. Open IntelliJ IDEA
2. Go to `Settings/Preferences` → `Plugins` → `Marketplace`
3. Search for "Conventional Commits Wizard"
4. Click `Install`
5. Restart the IDE

### Manual Installation

1. Download the latest release from
   the [releases page](https://github.com/nerdwarts/conventional-commits-wizard/releases)
2. Go to `Settings/Preferences` → `Plugins` → `⚙️` → `Install Plugin from Disk`
3. Select the downloaded `.zip` file
4. Restart the IDE

## 📖 Usage

1. Open the VCS commit dialog (`Ctrl+K` / `Cmd+K`)
2. Use the Conventional Commits Wizard to craft your message
3. Select a gitmoji and commit type
4. Add an optional scope
5. Write your commit description
6. Commit your changes

## 🎨 Commit Message Format

```
<type>(<optional-scope>): <optional-gitmoji> <description>
[optional body]
[optional footer(s)]
```

**Example:**

```
feat(auth): ✨ add OAuth2 authentication support
Implemented OAuth2 flow with support for Google and GitHub providers.
BREAKING CHANGE: Removed legacy API key authentication
```

## 🛠️ Development

### Requirements

- IntelliJ IDEA 2023.1 or later
- Gradle 8.0+
- Kotlin 1.9+

### Building from Source

```bash
git clone https://github.com/nerdwarts/conventional-commits-wizard.git
cd conventional-commits-wizard
./gradlew buildPlugin
```

The plugin will be available in build/distributions/.
Running in Development Mode

```bash
./gradlew runIde
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (git checkout -b feature/amazing-feature)
3. Commit your changes using conventional commits
4. Push to the branch (git push origin feature/amazing-feature)
5. Open a Pull Request

## 📝 License

This project is licensed under the Apache 2.0 License - see the LICENSE file for details.

## 💖 Support

If you find this plugin helpful, consider supporting its development:
<img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee"></img>

## 🐛 Issues & Feedback

Found a bug or have a feature request? Please open an issue
on [GitHub](https://github.com/nerdwarts/conventional-commits-wizard/issues).

## 📚 Resources

- [Conventional Commits Specification](https://www.conventionalcommits.org/en/v1.0.0/#summary)
- [Gitmoji Guide](https://gitmoji.dev/specification)
- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html?from=jetbrains.org)

<hr></hr>

Made with ❤️ by [nerdwarts](https://nerdwarts.com) 