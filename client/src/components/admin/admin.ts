export const admin = "260335@student.pwr.edu.pl";

export const accessAllowed = (token: any) => {
  return token?.email;
};
