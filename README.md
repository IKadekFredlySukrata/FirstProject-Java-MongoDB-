Preparation
1. MongoDB Compass
2. IDE (Visual Studio Code, IntelliJ, etc)
3. Source Code

How to Make the Code Run Properly

1. Install MongoDB Compass and add the bin path to the environment variables
2. Make a new connection
3. Connect to your new connection
4. Make a new database with the name "HotelProject"
5. Make a new collection with the names "Hotel" and "Room"
6. Restore the data by clicking the "Add Data" button then choose "Import JSON" and choose the JSON file (BasisDataProject.User.json >> User, and BasisDataProject.movie.json >> movie)

## JSON MongoDB Data

![Screenshot 2024-12-13 122044](https://github.com/user-attachments/assets/51ebaf71-2ead-46c0-b5dc-91603cd1639c)

## "Add Data" Button
![Screenshot 2024-12-13 121928](https://github.com/user-attachments/assets/a7866f9e-66fc-448f-8d03-d3182cb3b86b)

7. Update the "hotelpicturelocation" field by changing it to the absolute path of the pictures // These pictures are already included in the source code

## Hotel Picture Path Update
![image](https://github.com/user-attachments/assets/38e2ae7b-5942-4760-97bc-10273dadaa91)

8. Update the "roompicturelocation" field by changing it to the absolute path of the pictures // These pictures are already included in the source code

## Room Picture Path Update
![image](https://github.com/user-attachments/assets/a5b199c5-eb2c-407f-a803-24ba045ccaaf)


9. Lastly, you must change this part of every class code in the "View" folder. You'll need to change the absolute path of a picture named "Cinema_XXI.png"

## Part of the Code in "View" Folder That Needs to be Changed
```
private void initializeFrame() {
...

ImageIcon originalIcon = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\TUGAS 16 (Final Task)\\Bioskop\\src\\Cinema_XXI.png");
Image originalImage = originalIcon.getImage();

...

ImageIcon iconTopLeft = new ImageIcon("G:"Change this part of path"\\src\\Cinema_XXI.png");
frame.setIconImage(iconTopLeft.getImage());

...
}
