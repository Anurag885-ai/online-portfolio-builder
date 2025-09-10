document.getElementById("portfolioForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const formData = {
        name: document.getElementById("name").value,
        skills: document.getElementById("skills").value,
        projects: document.getElementById("projects").value,
        social: document.getElementById("social").value
    };

    const backendUrl = "http://localhost:8080";

    const preview = document.getElementById("preview");
    preview.innerHTML = `
        <h2>${formData.name}</h2>
        <p><strong>Skills:</strong> ${formData.skills}</p>
        <p><strong>Projects:</strong> ${formData.projects}</p>
        <p><strong>Social:</strong> ${formData.social}</p>
    `;

    try {
        const response = await fetch(`${backendUrl}/api/preview`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            document.getElementById("download").href = `${backendUrl}/api/download`;
            document.getElementById("download").style.display = "block";
        } else {
            alert("Error generating portfolio!");
        }
    } catch (err) {
        console.error(err);
        alert("Backend not reachable.");
    }
});
