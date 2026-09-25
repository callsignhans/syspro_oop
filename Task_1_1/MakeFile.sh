#!/bin/bash

# Остановка скрипта при любой ошибке
set -e
MAIN_CLASS="Main"                   # Полное имя главного класса (с пакетом)
SRC_DIR="src/main/java"             # Папка с исходным кодом
BUILD_DIR="build_manual"            # Корневая папка для ручной сборки

# Внутренние папки для порядка
OUT_DIR="$BUILD_DIR/classes"        # Сюда складываем скомпилированный код (.class)
DOC_DIR="$BUILD_DIR/docs"           # Сюда генерируем документацию
JAR_NAME="$BUILD_DIR/app.jar"       # Имя готового JAR-файла
MANIFEST="$BUILD_DIR/MANIFEST.MF"   # Файл манифеста

echo "========== 1. Подготовка папок =========="
rm -rf "$BUILD_DIR"
mkdir -p "$OUT_DIR"
mkdir -p "$DOC_DIR"

echo "========== 2. Поиск исходного кода =========="
SOURCES_LIST="$BUILD_DIR/sources.txt"
find "$SRC_DIR" -name "*.java" > "$SOURCES_LIST"

if [ ! -s "$SOURCES_LIST" ]; then
    echo "Ошибка: В папке $SRC_DIR не найдено файлов .java!"
    exit 1
fi
echo "Найдено файлов для компиляции: $(wc -l < "$SOURCES_LIST")"

echo "========== 3. Компиляция (javac) =========="
javac -d "$OUT_DIR" @"$SOURCES_LIST"
echo "Компиляция успешно завершена."

echo "========== 4. Генерация документации (javadoc) =========="
# Флаг -noindex и -notree отключают создание тяжелых общих указателей
# Флаг -linkoffline запрещает утилите ходить в интернет
javadoc -d "$DOC_DIR" \
        -sourcepath "$SRC_DIR" \
        -nonavbar \
        -noindex \
        -notree \
        "$SRC_DIR"/*.java -quiet
echo "Документация создана в: $DOC_DIR"


echo "========== 5. Создание Манифеста =========="
# Создаем файл, который скажет Java, какой класс запускать при старте JAR
echo "Manifest-Version: 1.0" > "$MANIFEST"
echo "Main-Class: $MAIN_CLASS" >> "$MANIFEST"
echo "" >> "$MANIFEST"

echo "========== 6. Упаковка в архив (jar) =========="
# c - создать архив, m - использовать манифест, f - имя файла
# -C переводит утилиту в папку с классами, чтобы внутри JAR не было лишней папки 'out'
jar cmf "$MANIFEST" "$JAR_NAME" -C "$OUT_DIR" .
echo "Создан исполняемый архив: $JAR_NAME"

echo "========== 7. Запуск приложения (java) =========="
echo "--------------------------------------------------"
java -jar "$JAR_NAME"
echo "--------------------------------------------------"
echo "Работа приложения завершена."
