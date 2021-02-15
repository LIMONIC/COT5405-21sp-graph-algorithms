@echo off
start /wait cmd /c "javac simulated_test.java"
start /wait cmd /c "javac real_test.java"
for /l %%i in (1, 1, 6) do (
echo "Graph type: %%i"
  for /l %%a in (100, 100, 3000) do (
    start /wait cmd /c "java -Xmx16000m simulated_test %%a %%i"
  )
)
echo "Finished!"
pause