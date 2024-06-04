document.addEventListener('DOMContentLoaded', function() {

    const homepageMain = document.querySelector('.homepage-main')

    const largeHTML = `<div class="sidebar-wrapper hidden" id="sidebar">

    <i class="fa-solid fa-xmark fa-2xl close-icon" id="close-icon"></i>
    
    <div class="sidebar-report">
        <h1 class="report-title">Report Trash</h1>
        <hr class="report-divider">
        <textarea name="Description" class="description-input" id="description" maxlength="256" cols="33" rows="5" placeholder="Description"></textarea>
        <div class="dropdown-tooltip">
            <div class="dropdown-severity">
                <button class="dropdown-button" id="severity-dropdown">
                    <span class="severity-button-left" id="selected-severity">
                        <img src="./assets/severity-icon.png" alt="" class="severity-icon">
                        Severity
                    </span>
                    <i class="fa-solid fa-angle-down fa-xl" id="rotate-icon"></i>
                </button>
                <div class="dropdown-content" id="dropdown-content">
                    <a class="dropdown-option severity-low" id="dropdown-option" href="">Low</a>
                    <a class="dropdown-option severity-medium" id="dropdown-option" href="">Medium</a>
                    <a class="dropdown-option severity-high" id="dropdown-option" href="">High</a>
                </div>
            </div>
            <div class="severity-tooltip">
                <button class="tooltip-button"><img src="./assets/tooltip-icon.png" alt="" class="tooltip-icon"></button>
                <div class="tooltip-popup">
                    <p class="tooltip-description"><span class="tdesc-low">Low: </span>Trash is not a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-medium">Medium: </span>Trash is a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-high">High: </span>Trash is a severe threat to the environment or animals.</p>
                </div>
            </div>
        </div>
        <div class="collected-wrapper">
            <input type="checkbox" name="collected" id="collected" class="collected-checkbox">
            <label for="collected" class="collected-label">Trash collected</label>
        </div>
        <button class="send-button" id="send-report">Submit <img src="./assets/send-icon.png" alt="" class="send-icon"></button>
    </div>
    
    <div class="chatbot-wrapper">
        <button class="sidebar-dropdown chatbot-dropdown" id="chatbot-button">
            <span class="sidebar-left">
                <img src="./assets/chatbot-icon.png" alt="" class="sidebar-icon globe-icon">
                <p class="sidebar-title">ChatBot</p>
            </span>
            <i class="fa-solid fa-angle-up fa-xl" id="chatbot-dropdown-icon"></i>
        </button>
        <div class="chatbot-content" id="chatbot-content">
            <iframe src="https://autoinsight-bot.vercel.app/blue-future-bqiq6kw" class="chatbot-iframe" title="ChatBot widget"></iframe>
        </div>
    </div>
    
    <div class="profile-sidebar">
        <div class="profile-sidebar-left">
            <img src="./assets/default-profile.webp" alt="" class="profile-image">
    
            <div class="profile-infos">
                <h1 class="profile-name">Guilherme</h1>
                <p class="profile-level">Level 5</p>
            </div>
        </div>
    
        <div class="profile-sidebar-right">
            <button class="settings-button"><img src="./assets/settings-icon.png" alt="" class="settings-icon"></button>
        </div>
    
    </div>
    
    </div>
    
    <div class="map-wrapper full-width" id="map-wrapper">
    
    <i class="fa-solid fa-bars-staggered fa-2xl hamburger-icon" id="hamburger-icon"></i>
    
    
    <div class="alert-popup" id="alert-popup">
        <img src="./assets/alert-popup.png" alt="" class="alert-image">
        <div class="alert-column">
            <h1 class="alert-title" id="alert-title">Alert Title</h1>
            <p class="alert-description" id="alert-description">Alert Description</p>
        </div>
    </div>
    
    <iframe src="https://bluefuture-trashmap.onrender.com" class="map" title="Map widget" id="iframe-map"></iframe>
    
    </div>` /* For large screens (>= 1200px) */
    const mediumHTML = `<div class="sidebar-wrapper hidden" id="sidebar">

    <i class="fa-solid fa-xmark fa-2xl close-icon" id="close-icon"></i>
    
    <div class="sidebar-report">
        <h1 class="report-title">Report Trash</h1>
        <hr class="report-divider">
        <textarea name="Description" class="description-input" id="description" maxlength="256" cols="33" rows="5" placeholder="Description"></textarea>
        <div class="dropdown-tooltip">
            <div class="dropdown-severity">
                <button class="dropdown-button" id="severity-dropdown">
                    <span class="severity-button-left" id="selected-severity">
                        <img src="./assets/severity-icon.png" alt="" class="severity-icon">
                        Severity
                    </span>
                    <i class="fa-solid fa-angle-down fa-xl" id="rotate-icon"></i>
                </button>
                <div class="dropdown-content" id="dropdown-content">
                    <a class="dropdown-option severity-low" id="dropdown-option" href="">Low</a>
                    <a class="dropdown-option severity-medium" id="dropdown-option" href="">Medium</a>
                    <a class="dropdown-option severity-high" id="dropdown-option" href="">High</a>
                </div>
            </div>
            <div class="severity-tooltip">
                <button class="tooltip-button"><img src="./assets/tooltip-icon.png" alt="" class="tooltip-icon"></button>
                <div class="tooltip-popup">
                    <p class="tooltip-description"><span class="tdesc-low">Low: </span>Trash is not a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-medium">Medium: </span>Trash is a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-high">High: </span>Trash is a severe threat to the environment or animals.</p>
                </div>
            </div>
        </div>
        <div class="collected-wrapper">
            <input type="checkbox" name="collected" id="collected" class="collected-checkbox">
            <label for="collected" class="collected-label">Trash collected</label>
        </div>
        <button class="send-button" id="send-report">Submit <img src="./assets/send-icon.png" alt="" class="send-icon"></button>
    </div>
    
    <div class="chatbot-wrapper">
        <button class="sidebar-dropdown chatbot-dropdown" id="chatbot-button">
            <span class="sidebar-left">
                <img src="./assets/chatbot-icon.png" alt="" class="sidebar-icon globe-icon">
                <p class="sidebar-title">ChatBot</p>
            </span>
            <i class="fa-solid fa-angle-up fa-xl" id="chatbot-dropdown-icon"></i>
        </button>
        <div class="chatbot-content" id="chatbot-content">
            <iframe src="https://autoinsight-bot.vercel.app/blue-future-bqiq6kw" class="chatbot-iframe" title="ChatBot widget"></iframe>
        </div>
    </div>
    
    <div class="profile-sidebar">
        <div class="profile-sidebar-left">
            <img src="./assets/default-profile.webp" alt="" class="profile-image">
    
            <div class="profile-infos">
                <h1 class="profile-name">Guilherme</h1>
                <p class="profile-level">Level 5</p>
            </div>
        </div>
    
        <div class="profile-sidebar-right">
            <button class="settings-button"><img src="./assets/settings-icon.png" alt="" class="settings-icon"></button>
        </div>
    
    </div>
    
    </div>
    
    <div class="map-wrapper full-width" id="map-wrapper">
    
    <i class="fa-solid fa-bars-staggered fa-2xl hamburger-icon" id="hamburger-icon"></i>
    
    
    <div class="alert-popup" id="alert-popup">
        <img src="./assets/alert-popup.png" alt="" class="alert-image">
        <div class="alert-column">
            <h1 class="alert-title" id="alert-title">Alert Title</h1>
            <p class="alert-description" id="alert-description">Alert Description</p>
        </div>
    </div>
    
    <iframe src="https://bluefuture-trashmap.onrender.com" class="map" title="Map widget" id="iframe-map"></iframe>
    
    </div>` /* For medium screens (992px - 1199px) */
    const smallHTML = `
    <div class="report-section">

    <div class="sidebar-report">
        <h1 class="report-title">Report Trash</h1>
        <hr class="report-divider">
        <textarea name="Description" class="description-input" id="description" maxlength="256" cols="33" rows="5" placeholder="Description"></textarea>
        <div class="dropdown-tooltip">
            <div class="dropdown-severity">
                <button class="dropdown-button" id="severity-dropdown">
                    <span class="severity-button-left" id="selected-severity">
                        <img src="./assets/severity-icon.png" alt="" class="severity-icon">
                        Severity
                    </span>
                    <i class="fa-solid fa-angle-down fa-xl" id="rotate-icon"></i>
                </button>
                <div class="dropdown-content" id="dropdown-content">
                    <a class="dropdown-option severity-low" id="dropdown-option" href="">Low</a>
                    <a class="dropdown-option severity-medium" id="dropdown-option" href="">Medium</a>
                    <a class="dropdown-option severity-high" id="dropdown-option" href="">High</a>
                </div>
            </div>
            <div class="severity-tooltip">
                <button class="tooltip-button"><img src="./assets/tooltip-icon.png" alt="" class="tooltip-icon"></button>
                <div class="tooltip-popup">
                    <p class="tooltip-description"><span class="tdesc-low">Low: </span>Trash is not a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-medium">Medium: </span>Trash is a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-high">High: </span>Trash is a severe threat to the environment or animals.</p>
                </div>
            </div>
        </div>
        <div class="collected-wrapper">
            <input type="checkbox" name="collected" id="collected" class="collected-checkbox">
            <label for="collected" class="collected-label">Trash collected</label>
        </div>
        <button class="send-button" id="send-report">Submit <img src="./assets/send-icon.png" alt="" class="send-icon"></button>
    </div>
    
    <div class="chatbot-wrapper">
        <button class="sidebar-dropdown chatbot-dropdown" id="chatbot-button">
            <span class="sidebar-left">
                <img src="./assets/chatbot-icon.png" alt="" class="sidebar-icon globe-icon">
                <p class="sidebar-title">ChatBot</p>
            </span>
            <i class="fa-solid fa-angle-up fa-xl" id="chatbot-dropdown-icon"></i>
        </button>
        <div class="chatbot-content" id="chatbot-content">
            <iframe src="https://autoinsight-bot.vercel.app/blue-future-bqiq6kw" class="chatbot-iframe" title="ChatBot widget"></iframe>
        </div>
    </div>
    
    <div class="profile-sidebar">
        <div class="profile-sidebar-left">
            <img src="./assets/default-profile.webp" alt="" class="profile-image">
    
            <div class="profile-infos">
                <h1 class="profile-name">Guilherme</h1>
                <p class="profile-level">Level 5</p>
            </div>
        </div>
    
        <div class="profile-sidebar-right">
            <button class="settings-button"><img src="./assets/settings-icon.png" alt="" class="settings-icon"></button>
        </div>
    
    </div>
    
</div>
    
<div class="map-section">
    
    <div class="map-wrapper full-width" id="map-wrapper">
    
        <i class="fa-solid fa-bars-staggered fa-2xl hamburger-icon" id="hamburger-icon"></i>
    
    
        <div class="alert-popup" id="alert-popup">
            <img src="./assets/alert-popup.png" alt="" class="alert-image">
            <div class="alert-column">
                <h1 class="alert-title" id="alert-title">Alert Title</h1>
                <p class="alert-description" id="alert-description">Alert Description</p>
            </div>
        </div>
    
        <iframe src="https://bluefuture-trashmap.onrender.com" class="map" title="Map widget" id="iframe-map"></iframe>
    
    </div>
    
</div>
    ` /* For small screens (768px - 991px) */
    const mobileHTML = `
    <div class="report-section">

    <div class="sidebar-report">
        <h1 class="report-title">Report Trash</h1>
        <hr class="report-divider">
        <textarea name="Description" class="description-input" id="description" maxlength="256" cols="33" rows="5" placeholder="Description"></textarea>
        <div class="dropdown-tooltip">
            <div class="dropdown-severity">
                <button class="dropdown-button" id="severity-dropdown">
                    <span class="severity-button-left" id="selected-severity">
                        <img src="./assets/severity-icon.png" alt="" class="severity-icon">
                        Severity
                    </span>
                    <i class="fa-solid fa-angle-down fa-xl" id="rotate-icon"></i>
                </button>
                <div class="dropdown-content" id="dropdown-content">
                    <a class="dropdown-option severity-low" id="dropdown-option" href="">Low</a>
                    <a class="dropdown-option severity-medium" id="dropdown-option" href="">Medium</a>
                    <a class="dropdown-option severity-high" id="dropdown-option" href="">High</a>
                </div>
            </div>
            <div class="severity-tooltip">
                <button class="tooltip-button"><img src="./assets/tooltip-icon.png" alt="" class="tooltip-icon"></button>
                <div class="tooltip-popup">
                    <p class="tooltip-description"><span class="tdesc-low">Low: </span>Trash is not a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-medium">Medium: </span>Trash is a threat to the environment or animals.</p>
                    <p class="tooltip-description"><span class="tdesc-high">High: </span>Trash is a severe threat to the environment or animals.</p>
                </div>
            </div>
        </div>
        <div class="collected-wrapper">
            <input type="checkbox" name="collected" id="collected" class="collected-checkbox">
            <label for="collected" class="collected-label">Trash collected</label>
        </div>
        <button class="send-button" id="send-report">Submit <img src="./assets/send-icon.png" alt="" class="send-icon"></button>
    </div>
    
    <div class="chatbot-wrapper">
        <button class="sidebar-dropdown chatbot-dropdown" id="chatbot-button">
            <span class="sidebar-left">
                <img src="./assets/chatbot-icon.png" alt="" class="sidebar-icon globe-icon">
                <p class="sidebar-title">ChatBot</p>
            </span>
            <i class="fa-solid fa-angle-up fa-xl" id="chatbot-dropdown-icon"></i>
        </button>
        <div class="chatbot-content" id="chatbot-content">
            <iframe src="https://autoinsight-bot.vercel.app/blue-future-bqiq6kw" class="chatbot-iframe" title="ChatBot widget"></iframe>
        </div>
    </div>
    
    <div class="profile-sidebar">
        <div class="profile-sidebar-left">
            <img src="./assets/default-profile.webp" alt="" class="profile-image">
    
            <div class="profile-infos">
                <h1 class="profile-name">Guilherme</h1>
                <p class="profile-level">Level 5</p>
            </div>
        </div>
    
        <div class="profile-sidebar-right">
            <button class="settings-button"><img src="./assets/settings-icon.png" alt="" class="settings-icon"></button>
        </div>
    
    </div>
    
</div>
    
<div class="map-section">
    
    <div class="map-wrapper full-width" id="map-wrapper">
    
        <i class="fa-solid fa-bars-staggered fa-2xl hamburger-icon" id="hamburger-icon"></i>
    
    
        <div class="alert-popup" id="alert-popup">
            <img src="./assets/alert-popup.png" alt="" class="alert-image">
            <div class="alert-column">
                <h1 class="alert-title" id="alert-title">Alert Title</h1>
                <p class="alert-description" id="alert-description">Alert Description</p>
            </div>
        </div>
    
        <iframe src="https://bluefuture-trashmap.onrender.com" class="map" title="Map widget" id="iframe-map"></iframe>
    
    </div>
    
</div>
    ` /* For extra small screens (< 768px) */

    function handleMediaQuery() {
        const width = document.documentElement.clientWidth

        if (width <= 767) {
            homepageMain.innerHTML = mobileHTML  /* Extra small screens */
        } else if (width <= 991) {
            homepageMain.innerHTML = smallHTML   /* Small screens */
        } else if (width <= 1199) {
            homepageMain.innerHTML = mediumHTML  /* Medium screens */
        } else {
            homepageMain.innerHTML = largeHTML   /* Large screens */
        }
    }

    window.addEventListener('resize', handleMediaQuery)
    handleMediaQuery()
})