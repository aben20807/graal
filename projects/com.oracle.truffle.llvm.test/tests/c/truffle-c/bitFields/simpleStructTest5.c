struct test {
  unsigned char val : 1;
};

int main() {
  struct test t;
  t.val = 1; // -1
  long val = t.val;
  return val + 1;
}
