document.addEventListener('DOMContentLoaded', function () {

    const sidebar = document.getElementById('sidebar')
    const mapWrapper = document.getElementById('map-wrapper')
    const hamburgerIcon = document.getElementById('hamburger-icon')
    const closeIcon = document.getElementById('close-icon')
    const severityDropdown = document.getElementById('severity-dropdown')
    const dropdownContent = document.getElementById('dropdown-content')
    const rotateIcon = document.getElementById('rotate-icon')
    const severityOptions = document.querySelectorAll('.dropdown-option')
    const chatbotButton = document.getElementById('chatbot-button')
    const chatbotContent = document.getElementById('chatbot-content')
    const sidebarReport = document.querySelector('.sidebar-report')
    const chatbotDropdownIcon = document.getElementById('chatbot-dropdown-icon')
    const sendReportButton = document.getElementById('send-report')
    const descriptionInput = document.getElementById('description')
    const selectedSeverity = document.getElementById('selected-severity')
    const iframeMap = document.getElementById('iframe-map')
    const checkboxCollected = document.getElementById('collected')
    const alertPopup = document.getElementById('alert-popup')
    const alertTitle = document.getElementById('alert-title')
    const alertDescription = document.getElementById('alert-description')

    /* Remove the "ghost space" when the page is loaded */
    sidebar.classList.add('display-none')
    alertPopup.classList.add('remove-popup')
    
    /* Ensuring the close sidebar icon doesn't show when the page is loaded */
    closeIcon.style.display = 'none'

    /* Function to show an alert pop up on the screen, replacing the default alert box */
    function showAlert(title, description) {
        alertTitle.textContent = title
        alertDescription.textContent = description

        alertPopup.classList.remove('remove-popup')

        /* This timeout is used to ensure the animation loads when the alert pop up appears */
        setTimeout(() => {
            alertPopup.classList.add('show-popup')
        }, 1)

        /* This timeout is used to remove the alert pop up after 10 seconds */
        setTimeout(() => {
            alertPopup.classList.remove('show-popup')

            /* This timeout is used to remove the alert pop up after the animation ends to prevent the "ghost space" */
            setTimeout(() => {
                alertPopup.classList.add('remove-popup')
            }, 501)
        }, 10000)
    }

    /* When the hamburger icon is clicked, the sidebar appears and the map is reduced to 76vw */
    hamburgerIcon.addEventListener('click', function () {
        sidebar.classList.remove('display-none')

        /* This timeout is used to prevent all those functions loading with the "display-none" class removal */
        setTimeout(() => {
            sidebar.classList.remove('hidden')
            mapWrapper.classList.remove('full-width')
            mapWrapper.classList.add('reduced-width')
            hamburgerIcon.style.display = 'none'
            closeIcon.style.display = 'block'
        }, 1)
    })

    /* When the close icon is clicked, the sidebar disappears and the map is increased to 98vw */
    closeIcon.addEventListener('click', function () {
        sidebar.classList.add('hidden')
        mapWrapper.classList.add('full-width')
        mapWrapper.classList.remove('reduced-width')
        closeIcon.style.display = 'none'
        hamburgerIcon.style.display = 'block'

        /* This timeout is used to remove the "ghost space" on the page without the sidebar */
        setTimeout(() => {
            sidebar.classList.add('display-none')
        }, 300)
    })

    /* When the severity button is clicked, the dropdown content appears */
    severityDropdown.addEventListener('click', function () {
            if (severityDropdown.classList.contains('not-allowed-cursor')) {
                return
            } else {
                dropdownContent.classList.toggle('show-dropdown-content')
                severityDropdown.classList.toggle('dropdown-active')
                rotateIcon.classList.toggle('rotate-icon')
            }
    })

    /* Select Low, Medium and High severity, and change the button according to the option selected */
    severityOptions.forEach(option => {
        option.addEventListener('click', function (event) {
            event.preventDefault()
            const severity = this.textContent.trim()
            severityDropdown.className = 'dropdown-button'
            severityDropdown.classList.add(`severity-${severity.toLowerCase()}`)
            selectedSeverity.innerHTML = `
                <img src="./assets/severity-icon.png" alt="" class="severity-icon">
                ${severity} Severity
            `
            dropdownContent.classList.remove('show-dropdown-content')
            severityDropdown.classList.remove('dropdown-active')
            rotateIcon.classList.remove('rotate-icon')
        })
    })

    /* When the chatbot dropdown is clicked, toggle the chatbot content and hide the sidebar report */
    chatbotButton.addEventListener('click', function () {
        if (chatbotContent.classList.contains('show-chatbot-content')) {
            chatbotContent.classList.remove('show-chatbot-content')

            /* This timeout is used to ensure the report container appears after the chatbot container closes */
            setTimeout(() => {
                chatbotContent.style.display = 'none'
                sidebarReport.classList.remove('hidden-sidebar-report')
            }, 400)
        } else {
            chatbotContent.style.display = 'flex'

            /* This timeout is used to ensure the animation loads after the chatbot container appears */
            setTimeout(() => {
                chatbotContent.classList.add('show-chatbot-content')
            }, 1)
            sidebarReport.classList.add('hidden-sidebar-report')
        }
        chatbotButton.classList.toggle('chatbot-active')
        chatbotDropdownIcon.classList.toggle('rotate-icon')
    })

    /* When the send report button is clicked, send the report to the map endpoint */
    sendReportButton.addEventListener('click', function () {
        const description = descriptionInput.value.trim()
        const severity = selectedSeverity.textContent.trim().split(' ')[0].toLowerCase()

        /* If the description is empty or the severity is not selected, alert the user and do not send the request */
        if (description === '' || severity === 'Severity') {
            showAlert('Missing fields', 'Please provide a description and select a severity level before submitting.')
            return
        }

        blockReportButton()

        function sendReport(position) {
            const lat = position.coords.latitude
            const lng = position.coords.longitude
            const trashCollected = checkboxCollected.checked

            /* JSON data to be sent to the map endpoint */
            const reportData = {
                user: 'Anonymous',
                description: description,
                severity: severity,
                collected: trashCollected,
                lat: lat,
                lng: lng
            }

            /* Send the report to the map endpoint via POST request */
            fetch('https://bluefuture-trashmap.onrender.com/report', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': '*/*',
                    'Accept-Encoding': 'gzip, deflate, br',
                    'Connection': 'keep-alive'
                },
                body: JSON.stringify(reportData)
            }).then(response => {
                if (response.status === 200) {
                    blockReportForm()
                } else if (response.status === 400) {
                    showAlert('Position error', 'Your report could not be sent because you are not in the water.')
                    unblockReportButton()
                } else {
                    showAlert('Unknown error', 'An error occurred while sending your report. Please try again later.')
                    unblockReportButton()
                }
            })
        }

        /* Show an error message if the user denies the location permission or an unknown error occurs */
        function showError(error) {
            switch (error.code) {
                case error.PERMISSION_DENIED:
                    showAlert('Permission error', 'User denied the permission for the device location.')
                    unblockReportButton()
                    break
                case error.POSITION_UNAVAILABLE:
                    showAlert('Position error', 'Location information is unavailable.')
                    unblockReportButton()
                    break
                case error.TIMEOUT:
                    showAlert('Timed out', 'The request to get user location timed out.')
                    unblockReportButton()
                    break
                case error.UNKNOWN_ERROR:
                    showAlert('Unknown error', 'An unknown error occurred.')
                    unblockReportButton()
                    break
            }
        }

        /* Get the user's location */
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(sendReport, showError)
            } else {
                showAlert('Device error', 'Geolocation is not supported by your browser')
            }
        }

        getLocation()
    })

    /* Block the report form and change the submit button style after the report is sent */
    function blockReportForm() {
        sendReportButton.classList.add('button-report-sent')
        sendReportButton.classList.remove('send-button')
        sendReportButton.classList.add('not-allowed-cursor')
        descriptionInput.classList.add('not-allowed-cursor')
        severityDropdown.classList.add('not-allowed-cursor')
        checkboxCollected.classList.add('not-allowed-cursor')
        sendReportButton.innerHTML = `Report Sent <img src="./assets/report-sent.png" alt="" class="send-icon">`
        descriptionInput.disabled = true
        severityDropdown.disabled = true
        checkboxCollected.disabled = true
        iframeMap.src = iframeMap.src
    }

    /* Block the report button when the report is being sent to prevent spam POST requests */
    function blockReportButton() {
        sendReportButton.classList.add('not-allowed-cursor')
        sendReportButton.disabled = true
    }

    /* Unblock the report button when the report is not sent to allow the user send it again */
    function unblockReportButton() {
        sendReportButton.classList.remove('not-allowed-cursor')
        sendReportButton.disabled = false
    }
})