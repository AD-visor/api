find . -name "build.gradle.kts" ! -path "./.gradle/*" ! -path "./build/*" | while read f; do
    dir=$(dirname "$f")
    mkdir -p "$dir"
done
