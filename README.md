# Лаборатори №9: Код хамралт — JaCoCo

**Оюутны нэр:** Т.Мөнхцэцэн  
**Оюутны код:** B232270025

## Ажлын орчин

`java -version`

```
openjdk version "17.0.20.1" 2026-08-18
OpenJDK Runtime Environment (build 17.0.20.1+1-1-26.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20.1+1-1-26.04-Ubuntu, mixed mode, sharing)
```

`mvn -version`

```
Apache Maven 3.9.12
Maven home: /usr/share/maven
Java version: 17.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.18.54.1-microsoft-standard-wsl2", arch: "amd64", family: "unix"
```

## Хэсэг A. Happy path тестүүд ба эхний хэмжилт

`ScholarshipCalculatorTest` дотор зөвхөн хэвийн урсгалын 5 тест бичсэн: `letterGrade(95) = A`, `letterGrade(85) = B`, `gpa([95, 85]) = 3.5`, `scholarship(3.9, 16, false) = 100`, `scholarship(3.5, 13, false) = 70`.

`results/jacoco-A.csv` болон `results/mvn-verify-A.txt`:

| Хэмжүүр | Covered | Missed | Хамралт |
| --- | ---: | ---: | --- |
| Line | 15 | 10 | 15/25 = 60% |
| Branch | 20 | 21 | 20/41 ≈ 49% |

Шар ромбо нь нөхцлийн зөвхөн нэг тал хамрагдсаныг харуулсан. `letterGrade`-ийн сөрөг ба 100-аас их оноо, C/D/F буцаалт, `gpa`-ийн `null` ба хоосон массив, C/D/default салаа, мөн `scholarship`-ийн exception, сахилгын зөрчил, кредит дутуу `&&` салаа, `gpa >= 3.0` нөхцөл улаанаар үлдсэн. Happy path зөвхөн A/B дүн болон 100, 70 хувийн тэтгэлгийн амжилттай замыг дамжсан.

## Хэсэг B. CFG ба branch 100%

`scholarship()`-ийн CFG `cfg.png` дээр байна. Зангилааг n0–n15 гэж дугаарласан. Ирмэг бүрийн хажууд тухайн ирмэгийг дайрсан тестийн методын нэрийг бичсэн.

Гурван функцэд үлдсэн салаа болон exception замыг `assertThrows`-оор нэмсний дараа нийт **23** тест хэрэгтэй болсон. Энэ тоо `results/mvn-verify-B.txt` доторх `Tests run: 23`-тай таарна.

`results/jacoco-B.csv`:

- `BRANCH_MISSED = 0`, `BRANCH_COVERED = 41` (branch 100%)
- `LINE_MISSED = 0`, `LINE_COVERED = 25` (line 100%)

JaCoCo `||` ба `&&`-ийн нөхцөл бүрийг тусдаа салаа гэж тоолдог. Тиймээс `scholarship`-ийн эхний `if`-ийн гурван үнэн утгад гурван тусдаа тест бичсэн: `scholarship_gpaNegative_throws`, `scholarship_gpaAbove4_throws`, `scholarship_creditsNegative_throws`. `gpa()`-ийн `switch` default салааг F дүнгээр (`gpa_score50_returns0`), голч хангасан ч кредит дутуу салааг `scholarship_gpa3_9_credits14_returns70` болон `scholarship_gpa3_5_credits11_returns50` тестаар хаасан.

Хэсэг B-ийн нэмэлт тест болон `cfg.png`-г AI туслахтай хамтран бэлдсэн. `results/mvn-verify-B.txt` болон `results/jacoco-B.csv` нь тэр тестийн `mvn verify` гаралт.

## Хэсэг C. Давталтын заагийн тестүүд

`gpa()`-ийн давталтад 0 / 1 / 2+ дүрмийг дараах тестаар шалгасан.

| Зааг | Тест | Оролт | Хүлээгдэх үр дүн |
| --- | --- | --- | --- |
| 0 удаа | `gpa_empty_throws` | `[]` | `IllegalArgumentException` |
| 0 удаа | `gpa_null_throws` | `null` | `IllegalArgumentException` |
| 1 удаа | `gpa_score95_returns4` | `[95]` | `4.0` |
| 2+ удаа | `gpa_scores95_85_75_65_returns2_5` | `[95, 85, 75, 65]` | `2.5` |

Эдгээр тест branch болон line хамралтыг өсгөхгүй: давталтын бие болон гарах салаа Хэсэг B-д аль хэдийн нэг удаа хамрагдсан, JaCoCo давталт хэдэн удаа эргэснийг тоолдоггүй. Гэсэн ч 0 удаа хоосон оролтын exception-ыг, 1 удаа нэг дүн дээрх хуваалтыг, 2+ удаа дүн бүрийг нийлбэрт нэмж байгаа эсэхийг шалгана — `total =` гэж хуримтлуулахгүй алдаа ижил салааг хамруулсан ч зөвхөн олон дүнгийн тестэд унана.

## Хэсэг D. Хамралт 100% — гэхдээ алдаа амьд

Спецификацийн зааг дээр `letterGrade(60)`, `letterGrade(59.99)`, `letterGrade(69.99)`, `letterGrade(0)`, `letterGrade(100)`, `letterGrade(89.99)`, `letterGrade(90)` тестүүдийг нэмсэн. Засвараас өмнө `mvn test` ажиллуулахад зөвхөн `letterGrade_score60_returnsD` унасан. `results/mvn-test-D-fail.txt` дотор `ScholarshipCalculatorTest.letterGrade_score60_returnsD:70 expected: <D> but was: <F>` мөр бий. Кодын `if (score > 60)` нь 60 оноог F болгож байсан бол спецификаци 60–69.99 оноог D гэсэн. Засварыг тусдаа commit-оор хийж `>`-ийг `>=` болгосон.

Branch хамралт 100% байхад энэ алдаа үл үзэгдэгч байсан, учир нь `score > 60` ба `score >= 60` хоёул ижил хоёр салаа үүсгэдэг: үнэн бол D, худал бол F. JaCoCo тухайн салаа гүйцэтгэгдсэн эсэхийг л тэмдэглэдэг бөгөөд харьцуулах оператор `>` эсвэл `>=` болохыг ялгадаггүй. 65 оноо D салааны үнэн талыг, 50 оноо худал талыг аль хэдийн дамжсан тул `results/jacoco-B.csv` дээр `BRANCH_MISSED = 0` гарсан. Зааг утга 60-ыг шалгасан `letterGrade_score60_returnsD` л `expected: <D> but was: <F>` зөрүүг илрүүлсэн. Хамралт кодын аль мөр, аль салаа ажилласныг хэмждэг. Спецификацийн зааг утга зөв бодогдсон эсэхийг хэмждэггүй.

Засварын дараа `results/mvn-verify-final.txt` дээр `Tests run: 32`, `BUILD SUCCESS`. `results/jacoco-final.csv` дээр `BRANCH_MISSED = 0`, `LINE_MISSED = 0`.

Хэсэг D-ийн зааг утгын тест болон дүгнэлтийг AI туслахтай хамтран бэлдсэн. `results/mvn-test-D-fail.txt` нь засвараас өмнөх, `results/mvn-verify-final.txt` нь засварын дараах ажиллалт.
