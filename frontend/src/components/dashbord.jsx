import "../App.css";
import { useState } from "react";

function Dashboard({ setScreen }) {
  const [contactName, setContactName] = useState("");
  const [isGroup, setIsGroup] = useState(false);
  const [contacts, setContacts] = useState([]);

  const handleAddContact = () => {
    if (!contactName.trim()) return;

    const newContact = {
      name: contactName,
      group: isGroup,
    };

    setContacts([...contacts, newContact]);
    setContactName("");
    setIsGroup(false);
  };

  const handleDelete = (index) => {
    const updated = contacts.filter((_, i) => i !== index);
    setContacts(updated);
  };

  const handleLogout = () => {
    chrome.storage.local.remove("token");
    setScreen("login");
  };

  return (
    <>
      <div className="header">WChatAlert</div>

      <div className="card">
        <h3>Alert Contacts</h3>

        <input
          className="input"
          placeholder="Contact Name"
          value={contactName}
          onChange={(e) => setContactName(e.target.value)}
        />

        <div style={{ marginBottom: "12px" }}>
          <input
            type="checkbox"
            checked={isGroup}
            onChange={() => setIsGroup(!isGroup)}
          />{" "}
          This is a group chat?
        </div>

        <button className="button" onClick={handleAddContact}>
          Add Contact
        </button>

        {/* Contact List */}
        <div style={{ marginTop: "20px" }}>
          {contacts.map((c, index) => (
            <div
              key={index}
              style={{
                padding: "8px",
                borderBottom: "1px solid #eee",
                display: "flex",
                justifyContent: "space-between",
              }}
            >
              <span>
                {c.name} {c.group && "(Group)"}
              </span>
              <span
                style={{ cursor: "pointer", color: "red" }}
                onClick={() => handleDelete(index)}
              >
                ❌
              </span>
            </div>
          ))}
        </div>

        <button
          style={{
            marginTop: "20px",
            width: "100%",
            padding: "8px",
            backgroundColor: "#128C7E",
            color: "white",
            border: "none",
            borderRadius: "6px",
            cursor: "pointer",
          }}
          onClick={handleLogout}
        >
          Logout
        </button>
      </div>
    </>
  );
}

export default Dashboard;