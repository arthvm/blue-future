document.addEventListener('DOMContentLoaded', function () {

    const sidebar = document.getElementById('sidebar')
    const mapWrapper = document.getElementById('map-wrapper')
    const hamburgerIcon = document.getElementById('hamburger-icon')
    const closeIcon = document.getElementById('close-icon')
    const severityDropdown = document.getElementById('severity-dropdown')
    const dropdownContent = document.getElementById('dropdown-content')
    const rotateIcon = document.getElementById('rotate-icon')
    const severityOptions = document.querySelectorAll('.dropdown-option')

    /* Remove the "ghost space" when the page is loaded */
    sidebar.classList.add('display-none')

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

        dropdownContent.classList.toggle('show-dropdown-content')
        severityDropdown.classList.toggle('dropdown-active')
        rotateIcon.classList.toggle('rotate-icon')

    })

    /* Select Low, Medium and High severity, and change the button according to the option selected */
    severityOptions.forEach(option => {

        option.addEventListener('click', function (event) {

            event.preventDefault()
            const severity = this.textContent.trim()
            severityDropdown.className = 'dropdown-button'
            severityDropdown.classList.add(`severity-${severity.toLowerCase()}`)
            severityDropdown.querySelector('.severity-button-left').innerHTML = `
                <img src="./assets/severity-icon.png" alt="" class="severity-icon">
                ${severity} Severity
            `

            dropdownContent.classList.remove('show-dropdown-content')
            severityDropdown.classList.remove('dropdown-active')
            rotateIcon.classList.remove('rotate-icon')

        })

    })
})
