## Hwp DSL

한컴 문서를 만들기 위한 DSL입니다. 

[hwplib](https://github.com/neolord0/hwplib) 을 기반으로 만들었기 때문에 해당 라이브러리에 대한 이해가 있으면 좋습니다.

[Kotlin html](https://github.com/Kotlin/kotlinx.html) 을 보고 hwp도 만들 수 있지 않을까에서 시작했습니다. 해당 라이브러리도 참고하세요.

### 기본 사용법

```kotlin
hwpFile.createHwp().hwp {
  body {
    section {
      + "안녕, 한글"
    }
  }
}.paperSize(PaperSize.B4).build(path)
```

### 기본 예제

테스트 코드 및 example을 확인하시면 됩니다.
