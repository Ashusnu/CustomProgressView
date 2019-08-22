# CustomProgressView

HOW TO USE:

###Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

###Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Ashusnu:CustomProgressView:Tag'
	}
  
  
  
  ###Methods:
  
  1) setProgress(int progress)                                  - > Update progress value for progress view:
  2) getProgrees()                                              - > Get int current value of the progress view:
  3) resetProgress()                                            - > Reset progress view to its orignal state:
  4) setProgressColor(int progressColor)                        - > Set Progress Color:
  5) setProgressBackgroundColor(int progressBackgroundColor)    - > Set Background Color for non-progressed part:
